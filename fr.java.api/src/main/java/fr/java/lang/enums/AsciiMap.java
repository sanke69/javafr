/**
 * Copyright (C) 2007-?XYZ Steve PECHBERTI <steve.pechberti@laposte.net>
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  
 * 
 * See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 *
**/
package fr.java.lang.enums;

public enum AsciiMap {
						// Décimal   Octal   Hex  Binaire   Caractère
						// -------   -----   ---  --------    ------
    CAR_NUL    ( 0x00),	// 000      000    00   00000000      CAR_NUL    (Null char.)
    CAR_SOH    ( 0x01),	// 001      001    01   00000001      SOH    (Start of Header)
    CAR_STX    ( 0x02),	// 002      002    02   00000010      STX    (Start of Text)
    CAR_ETX    ( 0x03),	// 003      003    03   00000011      ETX    (End of Text)
    CAR_EOT    ( 0x04),	// 004      004    04   00000100      EOT    (End of Transmission)
    CAR_ENQ    ( 0x05),	// 005      005    05   00000101      ENQ    (Enquiry)
    CAR_ACK    ( 0x06),	// 006      006    06   00000110      ACK    (Acknowledgment)
    CAR_BEL    ( 0x07),	// 007      007    07   00000111      BEL    (Bell)
    CAR_BS     ( 0x08),	// 008      010    08   00001000       BS    (Backspace)
    CAR_HT     ( 0x09),	// 009      011    09   00001001       HT    (Horizontal Tab)
    CAR_LF     ( 0x0A),	// 010      012    0A   00001010       LF    (Line Feed)
    CAR_VT     ( 0x0B),	// 011      013    0B   00001011       VT    (Vertical Tab)
    CAR_FF     ( 0x0C),	// 012      014    0C   00001100       FF    (Form Feed)
    CAR_CR     ( 0x0D),	// 013      015    0D   00001101       CR    (Carriage Return)
    CAR_SO     ( 0x0E),	// 014      016    0E   00001110       SO    (Shift Out)
    CAR_SI     ( 0x0F),	// 015      017    0F   00001111       SI    (Shift In)
    CAR_DLE    ( 0x10),	// 016      020    10   00010000      DLE    (Data Link Escape)
    CAR_DC1    ( 0x11),	// 017      021    11   00010001      DC1    (XON)(Device Control 1)
    CAR_DC2    ( 0x12),	// 018      022    12   00010010      DC2    (Device Control 2)
    CAR_DC3    ( 0x13),	// 019      023    13   00010011      DC3    (XOFF)(Device Control 3)
    CAR_DC4    ( 0x14),	// 020      024    14   00010100      DC4    (Device Control 4)
    CAR_NAK    ( 0x15),	// 021      025    15   00010101      NAK    (Negative Acknowledgement)
    CAR_SYN    ( 0x16),	// 022      026    16   00010110      SYN    (Synchronous Idle)
    CAR_ETB    ( 0x17),	// 023      027    17   00010111      ETB    (End of Trans. Block)
    CAR_CAN    ( 0x18),	// 024      030    18   00011000      CAN    (Cancel)
    CAR_EM     ( 0x19),	// 025      031    19   00011001       EM    (End of Medium)
    CAR_SUB    ( 0x1A),	// 026      032    1A   00011010      SUB    (Substitute)
    CAR_ESC    ( 0x1B),	// 027      033    1B   00011011      ESC    (Escape)
    CAR_FS     ( 0x1C),	// 028      034    1C   00011100       FS    (File Separator)
    CAR_GS     ( 0x1D),	// 029      035    1D   00011101       GS    (Group Separator)
    CAR_RS     ( 0x1E),	// 030      036    1E   00011110       RS    (Request to Send)(Record Separator)
    CAR_US     ( 0x1F),	// 031      037    1F   00011111       US    (Unit Separator)
    CAR_SP     ( 0x20),	// 032      040    20   00100000       SP    (Space)
    CAR_EXCLAM ( 0x21),	// 033      041    21   00100001        !    (exclamation mark)
    CAR_DQUOTE ( 0x22),	// 034      042    22   00100010        "    (double quote)
    CAR_NUMBER ( 0x23),	// 035      043    23   00100011        #    (number sign)
    CAR_DOLLAR ( 0x24),	// 036      044    24   00100100        $    (dollar sign)
    CAR_PERCEN ( 0x25),	// 037      045    25   00100101        %    (percent)
    CAR_AMPERS ( 0x26),	// 038      046    26   00100110        &    (ampersand)
    CAR_QUOTE  ( 0x27),	// 039      047    27   00100111        '    (single quote)
    CAR_LPAREN ( 0x28),	// 040      050    28   00101000        (    (left opening parenthesis)
    CAR_RPAREN ( 0x29),	// 041      051    29   00101001        )    (right closing parenthesis)
    CAR_ASTERI ( 0x2A),	// 042      052    2A   00101010        *    (asterisk)
    CAR_PLUS   ( 0x2B),	// 043      053    2B   00101011        +    (plus)
    CAR_COMMA  ( 0x2C),	// 044      054    2C   00101100        ,    (comma)
    CAR_MINUS  ( 0x2D),	// 045      055    2D   00101101        -    (minus or dash)
    CAR_DOT    ( 0x2E),	// 046      056    2E   00101110        .    (dot)
    CAR_FSLASH ( 0x2F),	// 047      057    2F   00101111        /    (forward slash)
    CAR_NUM_0  ( 0x30),	// 048      060    30   00110000        0
    CAR_NUM_1  ( 0x31),	// 049      061    31   00110001        1
    CAR_NUM_2  ( 0x32),	// 050      062    32   00110010        2
    CAR_NUM_3  ( 0x33),	// 051      063    33   00110011        3
    CAR_NUM_4  ( 0x34),	// 052      064    34   00110100        4
    CAR_NUM_5  ( 0x35),	// 053      065    35   00110101        5
    CAR_NUM_6  ( 0x36),	// 054      066    36   00110110        6
    CAR_NUM_7  ( 0x37),	// 055      067    37   00110111        7
    CAR_NUM_8  ( 0x38),	// 056      070    38   00111000        8
    CAR_NUM_9  ( 0x39),	// 057      071    39   00111001        9
    CAR_COLON  ( 0x3A),	// 058      072    3A   00111010        :    (colon)
    CAR_SCOLON ( 0x3B),	// 059      073    3B   00111011        ;    (semi-colon)
    CAR_LESS   ( 0x3C),	// 060      074    3C   00111100        <    (less than sign)
    CAR_EQUAL  ( 0x3D),	// 061      075    3D   00111101        =    (equal sign)
    CAR_MORE   ( 0x3E),	// 062      076    3E   00111110        >    (greater than sign)
    CAR_QUEST  ( 0x3F),	// 063      077    3F   00111111        ?    (question mark)
    CAR_AT     ( 0x40),	// 064      100    40   01000000        @    (AT symbol)
    CAR_CHAR_A ( 0x41),	// 065      101    41   01000001        A
    CAR_CHAR_B ( 0x42),	// 066      102    42   01000010        B
    CAR_CHAR_C ( 0x43),	// 067      103    43   01000011        C
    CAR_CHAR_D ( 0x44),	// 068      104    44   01000100        D
    CAR_CHAR_E ( 0x45),	// 069      105    45   01000101        E
    CAR_CHAR_F ( 0x46),	// 070      106    46   01000110        F
    CAR_CHAR_G ( 0x47),	// 071      107    47   01000111        G
    CAR_CHAR_H ( 0x48),	// 072      110    48   01001000        H
    CAR_CHAR_I ( 0x49),	// 073      111    49   01001001        I
    CAR_CHAR_J ( 0x4A),	// 074      112    4A   01001010        J
    CAR_CHAR_K ( 0x4B),	// 075      113    4B   01001011        K
    CAR_CHAR_L ( 0x4C),	// 076      114    4C   01001100        L
    CAR_CHAR_M ( 0x4D),	// 077      115    4D   01001101        M
    CAR_CHAR_N ( 0x4E),	// 078      116    4E   01001110        N
    CAR_CHAR_O ( 0x4F),	// 079      117    4F   01001111        O
    CAR_CHAR_P ( 0x50),	// 080      120    50   01010000        P
    CAR_CHAR_Q ( 0x51),	// 081      121    51   01010001        Q
    CAR_CHAR_R ( 0x52),	// 082      122    52   01010010        R
    CAR_CHAR_S ( 0x53),	// 083      123    53   01010011        S
    CAR_CHAR_T ( 0x54),	// 084      124    54   01010100        T
    CAR_CHAR_U ( 0x55),	// 085      125    55   01010101        U
    CAR_CHAR_V ( 0x56),	// 086      126    56   01010110        V
    CAR_CHAR_W ( 0x57),	// 087      127    57   01010111        W
    CAR_CHAR_X ( 0x58),	// 088      130    58   01011000        X
    CAR_CHAR_Y ( 0x59),	// 089      131    59   01011001        Y
    CAR_CHAR_Z ( 0x5A),	// 090      132    5A   01011010        Z
    CAR_LBRACK ( 0x5B),	// 091      133    5B   01011011        [    (left opening bracket)
    CAR_BSLASH ( 0x5C),	// 092      134    5C   01011100        \    (back slash)
    CAR_RBRACK ( 0x5D),	// 093      135    5D   01011101        ]    (right closing bracket)
    CAR_CIRCUM ( 0x5E),	// 094      136    5E   01011110        ^    (caret cirumflex)
    CAR_USCORE ( 0x5F),	// 095      137    5F   01011111        _    (underscore)
    CAR_QUOTE2 ( 0x60),	// 096      140    60   01100000        `
    CAR_CHAR_a ( 0x61),	// 097      141    61   01100001        a
    CAR_CHAR_b ( 0x62),	// 098      142    62   01100010        b
    CAR_CHAR_c ( 0x63),	// 099      143    63   01100011        c
    CAR_CHAR_d ( 0x64),	// 100      144    64   01100100        d
    CAR_CHAR_e ( 0x65),	// 101      145    65   01100101        e
    CAR_CHAR_f ( 0x66),	// 102      146    66   01100110        f
    CAR_CHAR_g ( 0x67),	// 103      147    67   01100111        g
    CAR_CHAR_h ( 0x68),	// 104      150    68   01101000        h
    CAR_CHAR_i ( 0x69),	// 105      151    69   01101001        i
    CAR_CHAR_j ( 0x6A),	// 106      152    6A   01101010        j
    CAR_CHAR_k ( 0x6B),	// 107      153    6B   01101011        k
    CAR_CHAR_l ( 0x6C),	// 108      154    6C   01101100        l
    CAR_CHAR_m ( 0x6D),	// 109      155    6D   01101101        m
    CAR_CHAR_n ( 0x6E),	// 110      156    6E   01101110        n
    CAR_CHAR_o ( 0x6F),	// 111      157    6F   01101111        o
    CAR_CHAR_p ( 0x70),	// 112      160    70   01110000        p
    CAR_CHAR_q ( 0x71),	// 113      161    71   01110001        q
    CAR_CHAR_r ( 0x72),	// 114      162    72   01110010        r
    CAR_CHAR_s ( 0x73),	// 115      163    73   01110011        s
    CAR_CHAR_t ( 0x74),	// 116      164    74   01110100        t
    CAR_CHAR_u ( 0x75),	// 117      165    75   01110101        u
    CAR_CHAR_v ( 0x76),	// 118      166    76   01110110        v
    CAR_CHAR_w ( 0x77),	// 119      167    77   01110111        w
    CAR_CHAR_x ( 0x78),	// 120      170    78   01111000        x
    CAR_CHAR_y ( 0x79),	// 121      171    79   01111001        y
    CAR_CHAR_z ( 0x7A),	// 122      172    7A   01111010        z
    CAR_LBRACE ( 0x7B),	// 123      173    7B   01111011        {    (left opening brace)
    CAR_VERBAR ( 0x7C),	// 124      174    7C   01111100        |    (vertical bar)
    CAR_RBRACE ( 0x7D),	// 125      175    7D   01111101        }    (right closing brace)
    CAR_TILDE  ( 0x7E),	// 126      176    7E   01111110        ~    (tilde)
    CAR_DEL    ( 0x7F),	// 127      177    7F   01111111      DEL    (delete)
    // CAR_Extension Table - Latin I version
    CAR_CCEDIL ( 0x80),	// 128      200    80   10000000       Cc
    CAR_uTREMA ( 0x81),	// 129      201    81   10000001        ü
    CAR_eAIGU  ( 0x82),	// 130      202    82   10000010        é
    CAR_aCIRCO ( 0x83),	// 131      203    83   10000011        â
    CAR_aTREMA ( 0x84),	// 132      204    84   10000100        ä
    CAR_aGRAVE ( 0x85),	// 133      205    85   10000101        à
    CAR_aCIRCL ( 0x86),	// 134      206    86   10000110       a°
    CAR_cCEDIL ( 0x87),	// 135      207    87   10000111        ç
    CAR_eCIRCO ( 0x88),	// 136      210    88   10001000        ê
    CAR_eTREMA ( 0x89),	// 137      211    89   10001001        ë
    CAR_eGRAVE ( 0x8A),	// 138      212    8A   10001010        è
    CAR_ITREMA ( 0x8B),	// 139      213    8B   10001011        Ï
    CAR_ICIRCO ( 0x8C),	// 140      214    8C   10001100        Î
    CAR_IGRAVE ( 0x8D),	// 141      215    8D   10001101        Ì
    CAR_ATREMA ( 0x8E),	// 142      216    8E   10001110        Ä
    CAR_ACIRCL ( 0x8F),	// 143      217    8F   10001111       A°
    CAR_EAIGU  ( 0x90),	// 144      220    90   10010000       E'
    CAR_ae     ( 0x91),	// 145      221    91   10010001       ae
    CAR_AE     ( 0x92),	// 146      222    92   10010010       AE
    CAR_oCIRCO ( 0x93),	// 147      223    93   10010011        ô
    CAR_oTREMA ( 0x94),	// 148      224    94   10010100        ö
    CAR_uCIRCO ( 0x95),	// 149      225    95   10010101        û
    CAR_uGRAVE ( 0x96),	// 150      226    96   10010110        ù
    CAR_yTREMA ( 0x97),	// 151      227    97   10010111        ÿ
    CAR_OTREMA ( 0x98),	// 152      230    98   10011000        Ö
    CAR_UTREMA ( 0x99),	// 153      221    99   10011001        Ü
    CAR_CDOLLR ( 0x9A),	// 154      232    9A   10011010
    CAR_LIVRES ( 0x9B),	// 155      233    9B   10011011        £
    CAR_YENG   ( 0x9C),	// 156      234    9C   10011100
    CAR_PtMARK ( 0x9D),	// 157      235    9D   10011101       Pt
    CAR_fMARK  ( 0x9E),	// 158      236    9E   10011110        f
    CAR_aAIGU  ( 0x9F),	// 159      237    9F   10011111
    CAR_iAIGU  ( 0xA0),	// 160      240    A0   10100000
    CAR_oAIGU  ( 0xA1),	// 161      241    A1   10100001
    CAR_uAIGU  ( 0xA2),	// 162      242    A2   10100010
    CAR_nTILDE ( 0xA3),	// 163      243    A3   10100011        ñ
    CAR_NTILDE ( 0xA4),	// 164      244    A4   10100100        Ñ
    CAR_aUNDER ( 0xA5),	// 165      245    A5   10100101
    CAR_oUNDER ( 0xA6),	// 166      246    A6   10100110
    CAR_IQUEST ( 0xA7),	// 167      247    A7   10100111
    CAR_RES168 ( 0xA8),	// 168      250    A8   10101000
    CAR_RES169 ( 0xA9),	// 169      251    A9   10101001
    CAR_HALF   ( 0xAA),	// 170      252    AA   10101010
    CAR_QUART  ( 0xAB),	// 171      253    AB   10101011
    CAR_IEXCLA ( 0xAC),	// 172      254    AC   10101100
    CAR_RBINAR ( 0xAD),	// 173      255    AD   10101101
    CAR_LBINAR ( 0xAE),	// 174      256    AE   10101110
    CAR_RES175 ( 0xAF),	// 175      257    AF   10101111
    CAR_RES176 ( 0xB0),	// 176      260    B0   10110000
    CAR_RES177 ( 0xB1),	// 177      261    B1   10110001
    CAR_RES178 ( 0xB2),	// 178      262    B2   10110010
    CAR_RES179 ( 0xB3),	// 179      263    B3   10110011
    CAR_RES180 ( 0xB4),	// 180      264    B4   10110100
    CAR_RES181 ( 0xB5),	// 181      265    B5   10110101
    CAR_RES182 ( 0xB6),	// 182      266    B6   10110110
    CAR_RES183 ( 0xB7),	// 183      267    B7   10110111
    CAR_RES184 ( 0xB8),	// 184      270    B8   10111000
    CAR_RES185 ( 0xB9),	// 185      271    B9   10111001
    CAR_RES186 ( 0xBA),	// 186      272    BA   10111010
    CAR_RES187 ( 0xBB),	// 187      273    BB   10111011
    CAR_RES188 ( 0xBC),	// 188      274    BC   10111100
    CAR_RES189 ( 0xBD),	// 189      275    BD   10111101
    CAR_RES190 ( 0xBE),	// 190      276    BE   10111110
    CAR_RES191 ( 0xBF),	// 191      277    BF   10111111
    CAR_RES192 ( 0xC0),	// 192      300    C0   11000000
    CAR_RES193 ( 0xC1),	// 193      301    C1   11000001
    CAR_RES194 ( 0xC2),	// 194      302    C2   11000010
    CAR_RES195 ( 0xC3),	// 195      303    C3   11000011
    CAR_RES196 ( 0xC4),	// 196      304    C4   11000100
    CAR_RES197 ( 0xC5),	// 197      305    C5   11000101
    CAR_RES198 ( 0xC6),	// 198      306    C6   11000110
    CAR_RES199 ( 0xC7),	// 199      307    C7   11000111
    CAR_RES200 ( 0xC8),	// 200      310    C8   11001000
    CAR_RES201 ( 0xC9),	// 201      311    C9   11001001
    CAR_RES202 ( 0xCA),	// 202      312    CA   11001010
    CAR_RES203 ( 0xCB),	// 203      313    CB   11001011
    CAR_RES204 ( 0xCC),	// 204      314    CC   11001100
    CAR_RES205 ( 0xCD),	// 205      315    CD   11001101
    CAR_RES206 ( 0xCE),	// 206      316    CE   11001110
    CAR_RES207 ( 0xCF),	// 207      317    CF   11001111
    CAR_RES208 ( 0xD0),	// 208      320    D0   11010000
    CAR_RES209 ( 0xD1),	// 209      321    D1   11010001
    CAR_RES210 ( 0xD2),	// 210      322    D2   11010010
    CAR_RES211 ( 0xD3),	// 211      323    D3   11010011
    CAR_RES212 ( 0xD4),	// 212      324    D4   11010100
    CAR_RES213 ( 0xD5),	// 213      325    D5   11010101
    CAR_RES214 ( 0xD6),	// 214      326    D6   11010110
    CAR_RES215 ( 0xD7),	// 215      327    D7   11010111
    CAR_RES216 ( 0xD8),	// 216      330    D8   11011000
    CAR_RES217 ( 0xD9),	// 217      331    D9   11011001
    CAR_RES218 ( 0xDA),	// 218      332    DA   11011010
    CAR_RES219 ( 0xDB),	// 219      333    DB   11011011
    CAR_RES220 ( 0xDC),	// 220      334    DC   11011100
    CAR_RES221 ( 0xDD),	// 221      335    DD   11011101
    CAR_RES222 ( 0xDE),	// 222      336    DE   11011110
    CAR_RES223 ( 0xDF),	// 223      337    DF   11011111
    CAR_ALPHA  ( 0xE0),	// 224      340    E0   11100000
    CAR_BETA   ( 0xE1),	// 225      341    E1   11100001
    CAR_GAMMA  ( 0xE2),	// 226      342    E2   11100010
    CAR_PI     ( 0xE3),	// 227      343    E3   11100011
    CAR_SIGMA  ( 0xE4),	// 228      344    E4   11100100
    CAR_RO     ( 0xE5),	// 229      345    E5   11100101
    CAR_MU     ( 0xE6),	// 230      346    E6   11100110
    CAR_PHI    ( 0xE7),	// 231      347    E7   11100111
    CAR_RES232 ( 0xE8),	// 232      350    E8   11101000
    CAR_RES233 ( 0xE9),	// 233      351    E9   11101001
    CAR_RES234 ( 0xEA),	// 234      352    EA   11101010
    CAR_DELTA  ( 0xEB),	// 235      353    EB   11101011
    CAR_INFINI ( 0xEC),	// 236      354    EC   11101100
    CAR_RES237 ( 0xED),	// 237      355    ED   11101101
    CAR_RES238 ( 0xEE),	// 238      356    EE   11101110
    CAR_RES239 ( 0xEF),	// 239      357    EF   11101111
    CAR_RES240 ( 0xF0),	// 240      360    F0   11110000
    CAR_RES241 ( 0xF1),	// 241      361    F1   11110001
    CAR_LESSEQ ( 0xF2),	// 242      362    F2   11110010
    CAR_MOREEQ ( 0xF3),	// 243      363    F3   11110011
    CAR_RES244 ( 0xF4),	// 244      364    F4   11110100
    CAR_RES245 ( 0xF5),	// 245      365    F5   11110101
    CAR_DIVIDE ( 0xF6),	// 246      366    F6   11110110
    CAR_NEARBY ( 0xF7),	// 247      367    F7   11110111
    CAR_RES248 ( 0xF8),	// 248      370    F8   11111000
    CAR_RES249 ( 0xF9),	// 249      371    F9   11111001
    CAR_RES250 ( 0xFA),	// 250      372    FA   11111010
    CAR_SQROOT ( 0xFB),	// 251      373    FB   11111011
    CAR_POWERN ( 0xFC),	// 252      374    FC   11111100
    CAR_SQUARE ( 0xFD),	// 253      375    FD   11111101
    CAR_RES254 ( 0xFE),	// 254      376    FE   11111110
    CAR_RES255 ( 0xFF);	// 255      377    FF   11111111
	
	public int bytes;
	
	private AsciiMap(int _value) {
		bytes = _value;
	}
	
	
};
