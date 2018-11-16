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
    {id: 1, spoilerProbability: 0.0, username: "TestUser", content: "I can't wait for the new season to be released", date: "2018-11-16 10:45"},
    {id: 1, spoilerProbability: 0.0, username: "TestUser", content: "I can't wait for the new season to be released", date: "2018-11-16 10:45"},
    {id: 1, spoilerProbability: 0.0, username: "TestUser", content: "I can't wait for the new season to be released", date: "2018-11-16 10:45"},
    {id: 1, spoilerProbability: 0.0, username: "TestUser", content: "I can't wait for the new season to be released", date: "2018-11-16 10:45"},
    {id: 1, spoilerProbability: 0.0, username: "TestUser", content: "I can't wait for the new season to be released", date: "2018-11-16 10:45"},
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
        console.log("CHAT: FETHCING MESSAGES");
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
                // TODO: replace with acutal data
                console.log(result)
                this.setState({messages: fakeMessages})
            })
            .catch(err => {
                console.log("CHAT:" + err.status);
            })
    }

    render() {
        return ([
            <List>
                {this.state.messages.map(msg => {
                    return (
                        <MessageView id = {msg.id} spoilerProbability={msg.spoilerProbability} content={msg.content}
                                  username={msg.username} date={msg.date} />
                    );
                })}
            </List>,
            this.messageArea()
        ])
    }

    messageArea() {
        return (
            <div style={{display: 'flex', flexDirection: 'row', padding: 8}}>
                <TextField multiline label="Message" style={{flexGrow: 1}}
                           onChange={(event) => {this.setState({currentMessage: event.target.value})}}/>
                <Button disabled={this.state.pendingRequest || this.state.currentMessage === ""}>
                    Send
                </Button>
            </div>
        );
    }
}

export default ChatView