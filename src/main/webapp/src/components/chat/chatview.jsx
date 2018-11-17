import React from 'react'

import Button from '@material-ui/core/Button'
import TextField from '@material-ui/core/TextField';
import List from '@material-ui/core/List';
import {SERVER_URL} from "../../pages/app";
import MessageView from "./messageview";

const fakeMessages = [
    {id: 1, spoilerProbability: 0.0, username: "TestUser", content: "I can't wait for the new season to be released", date: "2018-11-16 10:45"},
    {id: 1, spoilerProbability: 0.0, username: "TestUser", content: "I can't wait for the new season to be released", date: "2018-11-16 10:45"},
    {id: 1, spoilerProbability: 0.0, username: "TestUser", content: "I can't wait for the new season to be released", date: "2018-11-16 10:45"},
    {id: 1, spoilerProbability: 0.0, username: "TestUser", content: "I can't wait for the new season to be released", date: "2018-11-16 10:45"},
    {id: 1, spoilerProbability: 0.0, username: "TestUser", content: "I can't wait for the new season to be released", date: "2018-11-16 10:45"},
    {id: 1, spoilerProbability: 0.0, username: "TestUser", content: "I can't wait for the new season to be released", date: "2018-11-16 10:45"},
    {id: 1, spoilerProbability: 0.0, username: "TestUser", content: "I can't wait for the new season to be released", date: "2018-11-16 10:45"},
    {id: 1, spoilerProbability: 0.0, username: "TestUser", content: "I can't wait for the new season to be released", date: "2018-11-16 10:45"},
    {id: 1, spoilerProbability: 0.0, username: "TestUser", content: "I can't wait for the new season to be released", date: "2018-11-16 10:45"},
    {id: 1, spoilerProbability: 0.0, username: "TestUser", content: "I can't wait for the new season to be released", date: "2018-11-16 10:45"},
    {id: 1, spoilerProbability: 1.0, username: "TestUser", content: "I can't wait for the new season to be released", date: "2018-11-16 10:45"},
    {id: 1, spoilerProbability: 0.0, username: "TestUser", content: "I can't wait for the new season to be released", date: "2018-11-16 10:45"},
    {id: 1, spoilerProbability: 0.0, username: "TestUser", content: "I can't wait for the new season to be released", date: "2018-11-16 10:45"},
    {id: 1, spoilerProbability: 1.0, username: "TestUser", content: "I can't wait for the new season to be released", date: "2018-11-16 10:45"},
    {id: 1, spoilerProbability: 0.1, username: "TestUser", content: "I can't wait for the new season to be released", date: "2018-11-16 10:45"},
    {id: 1, spoilerProbability: 0.0, username: "TestUser", content: "I can't wait for the new season to be released", date: "2018-11-16 10:45"},
    {id: 1, spoilerProbability: 1.0, username: "TestUser", content: "I can't wait for the new season to be released", date: "2018-11-16 10:45"},
    {id: 1, spoilerProbability: 0.0, username: "TestUser", content: "I can't wait for the new season to be released", date: "2018-11-16 10:45"},
    {id: 1, spoilerProbability: 0.0, username: "TestUser", content: "I can't wait for the new season to be released", date: "2018-11-16 10:45"},
    {id: 1, spoilerProbability: 1.0, username: "TestUser", content: "I can't wait for the new season to be released", date: "2018-11-16 10:45"},
    {id: 1, spoilerProbability: 0.2, username: "TestUser", content: "I can't wait for the new season to be released", date: "2018-11-16 10:45"},
    {id: 1, spoilerProbability: 0.0, username: "TestUser", content: "I can't wait for the new season to be released", date: "2018-11-16 10:45"},
    {id: 1, spoilerProbability: 0.0, username: "TestUser", content: "I can't wait for the new season to be released", date: "2018-11-16 10:45"},
    {id: 1, spoilerProbability: 0.4, username: "TestUser", content: "I can't wait for the new season to be released", date: "2018-11-16 10:45"},
    {id: 1, spoilerProbability: 1.0, username: "TestUser", content: "I can't wait for the new season to be released", date: "2018-11-16 10:45"},
];

class ChatView extends React.Component {
    constructor(props) {
        super(props);
        this.fetchMessages = this.fetchMessages.bind(this);
    }


    state = {
        messages: [],
        pendingRequest: false,
        currentMessage: "",
    };

    componentDidMount() {
        this.fetchMessages()
    }

    fetchMessages() {
        const params = "userToken=" + this.props.context.userToken + "&showId=" + this.props.showId;
        fetch(SERVER_URL + "/auth/messages?" + params)
            .then(resp => {
                if (resp.status === 200) {
                    return resp.json()
                } else if (resp.status === 401) {
                    this.props.context.setUserToken("")
                }
            })
            .then(result => {
                console.log(result);
                this.setState({messages: result.messages})
            })
            .catch(err => {
                console.log("CHAT:" + err.status);
            })
    }

    sendCurrentMessage() {
        this.setState({pendingRequest: true});

        const url = SERVER_URL + "/auth/messages";
        const params = "userToken=" + this.props.context.userToken +
            "&showId=" + this.props.showId +
            "&content=" + this.state.currentMessage;
        fetch(url, {
            method: 'POST',
            body: params,
            headers: {'Content-type': "application/x-www-form-urlencoded; charset=UTF-8"}
        })
            .then(resp => {
                this.setState({pendingRequest: false});
                if (resp.status === 200) {
                    this.fetchMessages()
                } else if (resp.status === 401) {
                    this.props.context.setUserToken("")
                }
            })
    }

    render() {
        return ([
            <div id="messagesList" style={{height: '92%', overflow: 'auto'}}>
            <List tab>
                {this.state.messages.map(msg => {
                    return (
                        <MessageView id = {msg.id} spoilerProbability={msg.spoilerProbability} content={msg.content}
                                  username={msg.username} date={msg.date} userToken={this.props.context.userToken} />
                    );
                })}
            </List>
            </div>,
            this.messageArea()
        ])
    }

    messageArea() {
        return (
            <div style={{display: 'flex', flexDirection: 'row', padding: 8}}>
                <TextField multiline label="Message" style={{flexGrow: 1}}
                           onChange={(event) => {this.setState({currentMessage: event.target.value})}}/>
                <Button disabled={this.state.pendingRequest || this.state.currentMessage === ""}
                        onClick={this.sendCurrentMessage.bind(this)}>
                    Send
                </Button>
            </div>
        );
    }
}

export default ChatView