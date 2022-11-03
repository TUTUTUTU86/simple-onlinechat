import { useEffect, useState } from "react";
import * as SockJS from 'sockjs-client';
import * as StompJS from '@stomp/stompjs';
import { ActivationState } from "@stomp/stompjs";

class MessageService{

    constructor(setIsLoggedIn, setMessages){
        this.setIsLoggedIn = setIsLoggedIn;
        this.setMessages = null;
        this.client = null;
        this.isLoggined = false;
        this.isConnected = false;
        this.user = null;
        this.messages = [];
        this.users = new Map();
    }

    getMessages(){
        return this.messages;
    }

    getUsers(){
        return this.users;
    }

    getUser(){
        return this.user;
    }
    
    async fetchUsers(){
        await fetch('http://localhost:8080/getUsers', {
            method: 'GET',
            credentials: 'include'

        }).then((res) => { return res.json() }).then((users) => {
            console.log("Users: ", users);
            this.users = new Map();
            for(let user of users) {
                this.users.set(user.userID, user);
            }
            console.log("USerMap:", this.users);
            }
                );
            
    }

    async fetchLastMessages(){
        await fetch('http://localhost:8080/lastMsg', {
            method: 'GET',
            credentials: 'include'

        }).then((res) => {return res.json() }).then((obj) => {console.log(obj);})
    }

    async checkInviteCode(inviteCode){
        let status;
        console.log("In check inviteCode function");
        await fetch('http://localhost:8080/inviteCode', {
            method: 'POST',
            credentials: 'include',
            body: inviteCode
        }).then((res) =>{ 
            console.log(res);
            status =res.ok;});
        console.log('In check invite function status is ' + status);
        return status;
    }

    async register(username, password, inviteCode){
        console.log("in registration function");
        const regForm = {
            username: username,
            nickname: username,
            password: password,
            inviteCode: inviteCode

        };
        let status;
        let error;
        await fetch('http://localhost:8080/regAAA', {
            method: 'POST',
            credentials: 'include',
            body: JSON.stringify(regForm),
            headers: {
                'Content-Type': 'application/json'
            }
        }).then(async (res) =>{ 
            console.log(res);
            status = res.ok;
            error = await res.text()});
        console.log([status, error]);
        return new Array(status, error);
    }

    async requestInviteCode(){
        await fetch('http://localhost:8080/invite', {
            method: 'GET',
            credentials: 'include'
        }).then((res) => {
            console.log(res);
            return res.text();
        }).then((text) => {console.log(text)});
    }

    async authorizate(username, password, setError){
        return fetch('http://localhost:8080', {
            method: 'GET',
            headers: {
              Authorization: 'Basic ' +  btoa(username + ':' + password),
            },
            credentials: 'include'
          }).then(async (res) => {
            if(res.status === 401) console.log("Wrong username or password");
            else{
                this.user = await res.json();
                this.users.set(this.user.userId, this.user)
                this.client_init();
                this.isLoggined = true;
                console.log("isLoggined is true");
            }
          }
            ).catch((err) => {
                setError(err);
                console.log(err.message);});
        
    }

    client_init() {
        if(this.client !== null) return;
        this.client = new StompJS.Client({
            debug: function(str){
                console.log(str);
            },
            reconnectDelay: 5000,
            heartbeatIncoming: 4000,
            heartbeatOutgoing: 4000
        });
    
        this.client.webSocketFactory = function(){
            let sock_client = new SockJS('http://localhost:8080/regSocket');
            return sock_client;
        };
    
        this.client.onConnect = this.onConnect.bind(this);
    
        this.client.onStompError = function(frame){
            console.log('Broker reported error: ' + frame.headers['message']);
            console.log('Additional details: ' + frame.body);
        };

        this.client.activate();
    }

    onConnect(){

        this.client.subscribe('/chatroom', (message) => {
            console.log("Recieved msg: " + message);
            let obj = JSON.parse(message.body);
            obj.id = new Date();
            this.messages.push(obj);
            this.setMessages([...this.messages]);
            console.log("Messages:  ");
            console.log(this.messages);
        });
        this.client.subscribe('/users', (message) => {
            console.log("Connected user ", message);
        });
        this.isConnected = true;
        this.setIsLoggedIn(true);
        console.log("is Connected is true");
        this.fetchUsers();
        
    }

    sendMessage(msg) {
        if(this.client.connected){
            let body = {userId: this.user.userID, body: msg}
            this.client.publish({destination: "/app/message", body: JSON.stringify(body)});
        }
    }

}

export default MessageService;