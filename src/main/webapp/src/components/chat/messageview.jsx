import React from 'react';

import ListItem from '@material-ui/core/ListItem';
import ListItemText from '@material-ui/core/ListItemText';
import WarningIcon from '@material-ui/icons/Warning';
import Button from '@material-ui/core/Button'
import CheckIcon from '@material-ui/icons/Check'
import Typography from '@material-ui/core/Typography';
import IconButton from '@material-ui/core/IconButton'
import {SERVER_URL} from "../../pages/app";

class MessageView extends React.Component {
    state = {
        ignoredWarning: false,
        alreadyVoted: false,
    };

    vote(spoiler) {
        const url = SERVER_URL + "/auth/userVote";
        const params = "userToken=" + this.props.userToken +
            "&messageId=" + this.props.id +
            "&spoiler=" + spoiler.toString();
        fetch(url, {
            method: 'POST',
            body: params,
            headers: {'Content-type': "application/x-www-form-urlencoded; charset=UTF-8"}
        })
    }

    render() {
        if (this.props.spoilerProbability < 1.0 || this.state.ignoredWarning) {
            return this.renderMessage()
        } else {
            return this.renderWarning()
        }
    }

    renderMessage() {
        return (
            <ListItem divider="true">
                <ListItemText
                    primary={
                        <div style={{display: 'flex', flex: 'row'}}>
                            <Typography variant="h6" style={{display: 'flex', flexGrow: 1}}>{this.props.username}</Typography>
                            <Typography color="textSecondary">{this.props.date}</Typography>
                        </div>
                    }
                    secondary={
                        <Typography color="textSecondary">{this.props.content}</Typography>
                    }/>
                {this.renderVotingButtons()}
            </ListItem>
        )
    }

    renderVotingButtons() {
        if (this.props.spoilerProbability === 0.0 || this.state.alreadyVoted) {
            return;
        }
        if (this.props.spoilerProbability > 0.0 && !this.state.alreadyVoted) {
            const spoilerButton = this.state.ignoredWarning ? null : (
                <IconButton onClick={() => this.vote(true)}>
                    <WarningIcon color="error"/>
                </IconButton>
            );
            return([
                <IconButton onClick={() => this.vote(false)}>
                    <CheckIcon style={{color: '#2E7D32'}}/>
                </IconButton>,
                spoilerButton
            ]);
        }
    }

    renderWarning() {
        let backgroundStyle = {
            display: 'flex',
            backgroundColor: '#C62828',
            justifyContent: 'center',
            alignItems: 'center',
            flexGrow: 1,
            height: 76,
        };

        return (
            <div style={backgroundStyle}>
                <WarningIcon style={{color: '#FFF', marginRight: 8}}/>
                <Typography variant="h6" style={{color: '#FFF'}}>
                    This message contains a spoiler !
                </Typography>
                <Button style={{color: '#fff', marginLeft: 8, backgroundColor:'#B71C1C'}}
                        onClick={() => this.setState({ignoredWarning: true})}>
                    Reveal
                </Button>
            </div>
        )
    }
}

export default MessageView