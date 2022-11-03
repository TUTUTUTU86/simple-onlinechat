import MyMessage from "./MyMessage";
import {useEffect} from 'react';

function MyMessageContainer(props){
    console.log(props.messages);
    return(
    <div id="message-container" style={
        {
            backgroundColor: "black",
            width: '100vw',
            minHeight: 'calc(100vh - 300px)',
            display: 'flex',
            flexDirection: 'column-reverse',
            rowGap: '15px',
            justifyContent: 'end',
            paddingBottom: '100px',
            paddingTop: '200px',
            zIndex: 0
        }
    }>
        {props.messages.map((msg) =>  <MyMessage key={msg.id.getTime()} message={ msg.body} userId={msg.userId}> </MyMessage> ).reverse()}
    </div>
    );

}

export default MyMessageContainer;