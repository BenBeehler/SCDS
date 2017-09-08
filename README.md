# SCDS
Config Based Data Storage Server for querying and updating data from/with a path located in a config file in the server's file directories. (Beta)


This project is not ready for any form of use as an API/Stand Alone but instuctions for both are written below:

<br><br>
<h1>API:</1>
This project is written in java, include the libraries into your application...

<h2>For handling Connections:</h2>
Create a new class
make sure it extends <strong>ClientConnectEvent</strong>]
make sure it implements <strong>Listener</strong>
import all required methods
Go into your main manifest method and add 
<strong>ListenerManager.addListener(new MyClassICreatedEarlier());
  
<h2>For handling Disconnects:</h2>
Create a new class
make sure it extends <strong>ClientDisconnectEvent</strong>]
make sure it implements <strong>Listener</strong>
import all required methods
Go into your main manifest method and add 
<strong>ListenerManager.addListener(new MyClassICreatedEarlier());
  
<h2>For handling Queries:</h2>
Create a new class
make sure it extends <strong>DataClientSendEvent</strong>
make sure it implements <strong>Listener</strong>
import all required methods
Go into your main manifest method and add 
<strong>ListenerManager.addListener(new MyClassICreatedEarlier());


<h1>Stand Alone<h1>
- todo
