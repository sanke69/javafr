package fr.java.sdk.file.archive.multi.tar;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @author Kamran Zafar
 * 
 */
public class TarOutputStream extends FilterOutputStream {
    private TarEntry currentEntry;

    private long 	 bytesWritten;
    private long 	 currentFileSize;

    public TarOutputStream(OutputStream out) {
        super( out );
        bytesWritten = 0;
        currentFileSize = 0;
    }

    /**
     * Appends the EOF record and closes the stream
     * 
     * @see java.io.FilterOutputStream#close()
     */
    @Override
    public void close() throws IOException {
        closeCurrentEntry();
        write( new byte[TarUtils.EOF_BLOCK] );
        super.close();
    }

    /**
     * Writes a byte to the stream and updates byte counters
     * 
     * @see java.io.FilterOutputStream#write(int)
     */
    @Override
    public void write(int b) throws IOException {
        super.write( b );
        bytesWritten += 1;

        if (currentEntry != null) {
            currentFileSize += 1;
        }
    }

    /**
     * Checks if the bytes being written exceed the current entry size.
     * 
     * @see java.io.FilterOutputStream#write(byte[], int, int)
     */
    @Override
    public void write(byte[] b, int off, int len) throws IOException {
        if (currentEntry != null && !currentEntry.isDirectory()) {
            if (currentEntry.getSize() < currentFileSize + len) {
                throw new IOException( "The current entry[" + currentEntry.getName() + "] size["
                        + currentEntry.getSize() + "] is smaller than the bytes[" + ( currentFileSize + len )
                        + "] being written." );
            }
        }

        super.write( b, off, len );
    }

    /**
     * Writes the next tar entry header on the stream
     * 
     * @param entry
     * @throws IOException
     */
    public void putNextEntry(TarEntry entry) throws IOException {
        closeCurrentEntry();

        byte[] header = new byte[TarUtils.HEADER_BLOCK];
        entry.writeEntryHeader( header );

        write( header );

        currentEntry = entry;
    }

    /**
     * Closes the current tar entry
     * 
     * @throws IOException
     */
    protected void closeCurrentEntry() throws IOException {
        if (currentEntry != null) {
            if (currentEntry.getSize() > currentFileSize) {
                throw new IOException( "The current entry[" + currentEntry.getName() + "] of size["
                        + currentEntry.getSize() + "] has not been fully written." );
            }

            currentEntry = null;
            currentFileSize = 0;

            pad();
        }
    }

    /**
     * Pads the last content block
     * 
     * @throws IOException
     */
    protected void pad() throws IOException {
        if (bytesWritten > 0) {
            int extra = (int) ( bytesWritten % TarUtils.DATA_BLOCK );

            if (extra > 0) {
                write( new byte[TarUtils.DATA_BLOCK - extra] );
            }
        }
    }
}
