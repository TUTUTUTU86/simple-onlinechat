import { useContext, useState } from "react";
import { MessageServiceContest } from "../context";
import "./lp-styles.css"
import RegistrationModal from "../components/RegistrationModal";

function LoginPage(props){

    const {messageService} = useContext(MessageServiceContest);
    const [loginError, setLoginError] = useState(null);

    const [isRegWindowVisible, setIsRegWindowVisible] = useState(false);

    const connectToServer = function(username, password, setError) {
        messageService.authorizate(username, password, setError);
    }

    const connect = function(e){
        e.preventDefault();
        const username = document.getElementById("username_input").value;
        const password = document.getElementById("password_input").value;
        connectToServer(username, password, setLoginError);
    }

    const register = function(e){
        setIsRegWindowVisible(true);
        e.stopPropagation(); 
        console.log("click on button");
    }

    
    return(
        <div id="login-window-container" onClick={(e) => {console.log("click in lwc"); setIsRegWindowVisible(false); }}>
            { loginError != null && <div className="error-modal"> Server is down <button className="error-button" onClick={() => {setLoginError(null)}}>OK</button> </div> }
            <div id="login-window" >
                <form id="login-form" onSubmit={connect}>
                    <div className="form-field-container">
                    <div> Enter your username:</div>
                    <input className="form-input" type='text' id="username_input"></input>
                    </div>
                    <div className="form-field-container">
                        <div> Enter your password:</div>
                        <input className="form-input" type='password' id="password_input"></input>
                    </div>
                    <input id="submit-button" type='submit' style={{display: 'none'}} />
                </form>

    
            </div>
            <button className="register-button" onClick={register}>Register with invite code</button>

            { isRegWindowVisible && <RegistrationModal isVisible={true} setIsVisible={setIsRegWindowVisible}> </RegistrationModal> }
            

        </div>
    );
}

export default LoginPage;