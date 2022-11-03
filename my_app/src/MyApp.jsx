import * as SockJS from 'sockjs-client';
import * as StompJS from '@stomp/stompjs';

function MyApp(){
    const client = new StompJS.Client({
        debug: function(str){
            console.log(str);
        },
        reconnectDelay: 5000,
        heartbeatIncoming: 4000,
        heartbeatOutgoing: 4000
    }
    );
    client.webSocketFactory = function(){
        return new SockJS('http://localhost:8080/regSocket');
    };

    client.onConnect = function (frame){
        client.subscribe('/chatroom', (message) => {
            console.log("Recieved msg: " + message);
        });
    };

    client.onStompError = function(frame){
        console.log('Broker reported error: ' + frame.headers['message']);
        console.log('Additional details: ' + frame.body);
    };

    client.activate();

    function send(body) {
        client.publish({destination: "/app/message", body: "Hello!"});
    }
    
    return (
        <div>
            <button onClick={send}>Send Hello</button>
            <div>Hello world!</div>
        </div>
        
    );
}

export default MyApp;