import MyMessage from "../components/MyMessage";
import MyMessageContainer from "../components/MyMessageContainer";
import MyHeader from "../components/MyHeader";
import MyInputUI from "../components/MyInputUI";
import LoginPage from "./LoginPage";
import {useState} from 'react';
import { useContext } from "react";
import { MessageServiceContest } from "../context";

function Chat(){
    const {messageService, isLoggedIn} = useContext(MessageServiceContest);
    const [messages, setMessages] = useState([]);
    messageService.setMessages = setMessages;

    if(isLoggedIn)
    {
        return (
            <div style={{
                backgroundColor: 'pink',
                width: '100vw',
                height: '100vh'
            }}>
                <MyHeader> </MyHeader>
        
                <MyMessageContainer messages={messages}> </MyMessageContainer>
            
                <MyInputUI> </MyInputUI>
        
            </div>
        );

    }
    else{
        return (<LoginPage></LoginPage>);
    }


}


export default Chat;