import './App.css';
import LoginPage from './pages/LoginPage';
import Chat from './pages/Chat';
import {useState} from 'react';
import MessageService from './MessageService'
import {MessageServiceContest} from './context/index';
const messageService = new MessageService();
function App() {
  
  const [isLoggedIn, setIsLoggedIn] = useState(messageService.client !== null && messageService.client.connected);
  messageService.setIsLoggedIn = setIsLoggedIn;

  return (
    <MessageServiceContest.Provider value={
      {
        messageService,
        isLoggedIn
      }
    }>
      <Chat>
      </Chat>
      
    </MessageServiceContest.Provider>
    );
  
  }
export default App;
