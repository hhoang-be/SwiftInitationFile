# SwiftInitiationFile
Generate Swift initiation file from XML payload
Prefix (1 byte): Record Separator character (ASCII 0x1e)
Length (6 bytes): length (in bytes) of the Signature and DataPDU fields. This length is base-10 encoded as six ASCII characters, and is left-padded with zeros, if needed.
Signature (24 bytes) : signature computed on the DataPDU using the HMAC-SHA256 algorithm: the first 128 bits are base64-encoded.

This signature authenticates the originator of the DataPDU (the application or Alliance Access) and guarantees the integrity of the DataPDU. This action is referred to as local authentication (LAU). If Alliance Access is configured to not require LAU, then the field must contain NULL characters (ASCII 0x0).

DataPDU: XML structure containing the information relevant to be processed (message or report) encoded in UTF-8. The first byte of this field must be the character < (ASCII 0x3C). A byte- order marker is not supported.
