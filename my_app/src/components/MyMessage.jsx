import { useContext, useEffect, useState } from "react";
import { MessageServiceContest } from "../context";
import "./styles.css"

function MyMessage(props){
    console.log("Message");
    console.log(props.message);
    console.log(props.userId);
    console.log("MAP");
    const {messageService} = useContext(MessageServiceContest)
    const senderNickname = messageService.getUsers().get(props.userId).nickname
    const userAvatarURL = messageService.getUsers().get(props.userId).avatarURL
    const isUserMessage =  (props.userId === messageService.getUser().userId);
    const [isVisible, setIsVisible] = useState(false);

    const scrollToEnd = () => {
        window.scrollTo(0, document.body.scrollHeight);
        setTimeout(setIsVisible(true), 100);
    }

    useEffect(() => { scrollToEnd() })

    return(
        <div className={ (isVisible ? "" : "hidden") +" "+(isUserMessage ? "userMessage" : "") } id="myMessageContainer">
            <div id="userInfoContainer">

                <div id="userImg">
                    <img src={"data:image/png;base64," + userAvatarURL} width={1600} height={1200}></img>
                </div>

                <div  id="userName">
                    {senderNickname}
                </div>

            </div>
            
            <div id="messageContainer">
                <div id="message">
                    {props.message}
                </div>
            </div>
        </div>
    );


}

export default MyMessage;