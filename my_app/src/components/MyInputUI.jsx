import { useContext } from "react";
import { useState } from "react";
import { MessageServiceContest } from "../context";

function MyInputUI(){
    const [input, setInput] = useState("");
    const {messageService} = useContext(MessageServiceContest);

    const _send = () => {
       messageService.sendMessage(input);
    }

    return (
        <div style= {{
            position: 'fixed',
            bottom: '0',
            left: '0',
            right: '0',
            height:'100px',
            backgroundColor: 'white',
            display: 'flex',
            alignItems: 'center',
            justifyContent: 'center',
            columnGap: '50px'
        }}>

            <input type="text" style={
                {
                    width: '50%',
                    height: '30%',


                }
            } onChange={(e) => {
                setInput(e.target.value);
            }}/>
            <button style={
                {
                    width: '10%',
                    height: '30%',
                    border: 'none',
                    backgroundColor: 'green',
                    color: 'white'
                }
            } onClick={_send}>
                SEND!
            </button>
    </div>

    );

}

export default MyInputUI;