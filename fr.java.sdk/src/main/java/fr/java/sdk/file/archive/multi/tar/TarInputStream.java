package fr.java.sdk.file.archive.multi.tar;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

import fr.java.lang.exceptions.NotSupportedException;

public class TarInputStream extends FilterInputStream {
    private static final int SKIP_BUFFER_SIZE = 2_048;

    private TarEntry 	currentEntry;

    private long 		currentFileSize;
    private long 		bytesRead;
    private boolean 	defaultSkip = false;

    public TarInputStream(InputStream in) {
        super( in );
        currentFileSize = 0;
        bytesRead = 0;
    }

    /**
     * Read a byte
     * 
     * @see java.io.FilterInputStream#read()
     */
    @Override
    public int 					read() throws IOException {
        byte[] buf = new byte[1];

        int res = this.read( buf, 0, 1 );

        if (res != -1) {
            return buf[0];
        }

        return res;
    }
    /**
     * Checks if the bytes being read exceed the entry size and adjusts the byte
     * array length. Updates the byte counters
     * 
     * @see java.io.FilterInputStream#read(byte[], int, int)
     */
    @Override
    public int 					read(byte[] b, int off, int len) throws IOException {
        if(currentEntry != null) {
            if(currentFileSize == currentEntry.getSize())
                return -1;
            else if(( currentEntry.getSize() - currentFileSize ) < len)
                len = (int) ( currentEntry.getSize() - currentFileSize );
        }

        int br = super.read( b, off, len );
        if(br != -1) {
            if(currentEntry != null)
                currentFileSize += br;
            bytesRead += br;
        }

        return br;
    }

    @Override
    public boolean 				markSupported() {
        return false;
    }
    /**
     * Not supported
     */
    @Override
    public synchronized void 	mark(int readlimit) {
    	throw new NotSupportedException();
    }
    /**
     * Not supported
     */
    @Override
    public synchronized void 	reset() throws IOException {
        throw new IOException( "mark/reset not supported" );
    }

    /**
     * Returns the next entry in the tar file
     * 
     * @return TarEntry
     * @throws IOException
     */
    public TarEntry getNextEntry() throws IOException {
        closeCurrentEntry();

        byte[] header = new byte[TarUtils.HEADER_BLOCK];
        byte[] theader = new byte[TarUtils.HEADER_BLOCK];
        int tr = 0;

        // Read full header
        while (tr < TarUtils.HEADER_BLOCK) {
            int res = read( theader, 0, TarUtils.HEADER_BLOCK - tr );

            if (res < 0) {
                break;
            }

            System.arraycopy( theader, 0, header, tr, res );
            tr += res;
        }

        // Check if record is null
        boolean eof = true;
        for (byte b : header) {
            if (b != 0) {
                eof = false;
                break;
            }
        }

        if (!eof) {
            bytesRead += header.length;
            currentEntry = new TarEntry( header );
        }

        return currentEntry;
    }

    /**
     * Closes the current tar entry
     * 
     * @throws IOException
     */
    protected void closeCurrentEntry() throws IOException {
        if (currentEntry != null) {
            if (currentEntry.getSize() > currentFileSize) {
                // Not fully read, skip rest of the bytes
                long bs = 0;
                while (bs < currentEntry.getSize() - currentFileSize) {
                    long res = skip( currentEntry.getSize() - currentFileSize - bs );

                    if (res == 0 && currentEntry.getSize() - currentFileSize > 0) {
                        throw new IOException( "Possible tar file corruption" );
                    }

                    bs += res;
                }
            }

            currentEntry = null;
            currentFileSize = 0L;
            skipPad();
        }
    }

    /**
     * Skips the pad at the end of each tar entry file content
     * 
     * @throws IOException
     */
    protected void skipPad() throws IOException {
        if (bytesRead > 0) {
            int extra = (int) ( bytesRead % TarUtils.DATA_BLOCK );

            if (extra > 0) {
                long bs = 0;
                while (bs < TarUtils.DATA_BLOCK - extra) {
                    long res = skip( TarUtils.DATA_BLOCK - extra - bs );
                    bs += res;
                }
            }
        }
    }

    /**
     * Skips 'n' bytes on the InputStream<br>
     * Overrides default implementation of skip
     * 
     */
    @Override
    public long skip(long _n) throws IOException {
        if (defaultSkip) {
            // use skip method of parent stream
            // may not work if skip not implemented by parent
            return super.skip( _n );
        }

        if (_n <= 0) {
            return 0;
        }

        long left = _n;
        byte[] sBuff = new byte[SKIP_BUFFER_SIZE];

        while (left > 0) {
            int res = read( sBuff, 0, (int) ( left < SKIP_BUFFER_SIZE ? left : SKIP_BUFFER_SIZE ) );
            if (res < 0) {
                break;
            }
            left -= res;
        }

        return _n - left;
    }

    public void setDefaultSkip(boolean _defaultSkip) {
        defaultSkip = _defaultSkip;
    }
    public boolean isDefaultSkip() {
        return defaultSkip;
    }

}
