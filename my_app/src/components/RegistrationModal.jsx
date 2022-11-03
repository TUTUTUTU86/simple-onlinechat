import { useContext } from "react";
import { useEffect } from "react";
import { useState } from "react"
import { MessageServiceContest } from "../context";
import "./rm-styles.css"

const RegistrationModal = ({isVisible, setIsVisible}) => {

    const {messageService} = useContext(MessageServiceContest);

    const [isMainFieldsVisible, setIsMainFieldsVisible] = useState(false);
    const [errorMessage, setErrorMessage] = useState("");
    const [successMsg, setSuccessMsg] = useState("");
    const [inviteCodeErrorMsg, setInviteCodeErrorMsg] = useState("");
    const [inviteSuccessMsg, setInviteSuccessMsg] = useState("");
    const [inviteCode, setInviteCode] = useState("");


    const checkInviteCode = (inviteCode) => {
        return messageService.checkInviteCode(inviteCode);

    }
    const register = (e) => {
        e.preventDefault();
        console.log("In register fucntion in RegistrationModal");
        if(isMainFieldsVisible){
            const username = document.getElementById("reg-username-input").value;
            const password = document.getElementById("reg-password-input").value;
            messageService.register(username, password, inviteCode)
            .then(([status, errorMessage]) => {
                if (status){
                setSuccessMsg("Success!");
                setErrorMessage("");
            }
            else 
                setErrorMessage(errorMessage); });
    
    
            
                
        }else{
            setInviteCode(document.getElementById("invite-code-input").value);
            const status = checkInviteCode(document.getElementById("invite-code-input").value);
            status.then((result) => {
                console.log(result);
                if(!result) setInviteCodeErrorMsg("Invalid invite code!"); 
                else {
                    setIsMainFieldsVisible(result);
                    setInviteSuccessMsg("Invite code is valid!");
                    setInviteCodeErrorMsg("");
                        }
                        });
        }
    }

    return (
        <div className={isVisible ? "" : "hidden"} onClick={(e) => {e.stopPropagation();}}>
            <form id="registration-form" onSubmit={register} >
                <div>
                    <label >Enter your invite code:</label>
                    <input id="invite-code-input" type="text" ></input> 
                    <label className="registration-error-field" >{inviteCodeErrorMsg}</label>
                    <label className="registration-success-field"> {inviteSuccessMsg}</label>
                </div>
                <div className={isMainFieldsVisible ? "" : "hidden"}>
                    <div className="registration-field">
                        <label>Enter your username:</label>
                        <input id="reg-username-input" type="text"></input>
                    </div>
                    
                    <div className="registration-field">
                        <label>Enter your password:</label>
                        <input id="reg-password-input" type="password"></input>
                    </div>
                    <label className="registration-error-field">{errorMessage}</label>
                    <label className="registration-success-field">{successMsg}</label>
                    <input id="submit-button" type='submit' style={{display: 'none'}} />
                </div>
            </form>
        </div>
    );


}

export default RegistrationModal