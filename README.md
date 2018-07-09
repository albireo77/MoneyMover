# MoneyMover
Money Mover REST Application

GET request format invocation:
/moneymover/transfer/{code}/{iban}/{amount}

where:  
{code} - transfer code for incoming/outgoing Credit Transfer or Direct Debit (available values: ctin, ctout, ddin, ddout)  
{iban} - account IBAN (available values: PL10105000997603123456789123, TR320010009999901234567890, LU120010001234567891, QA54QNBA000000000000693123456, NO8330001234567)  
{amount} - transferred amount

After successful compilation, execute the following command in workspace directory to start application along with attached HTTP server:
java -jar target\MoneyMover-jar-with-dependencies.jar

Example invocation:
http://localhost:8080/moneymover/transfer/ddin/LU120010001234567891/6

Hostname and port can be set in moneymover.properties file.

