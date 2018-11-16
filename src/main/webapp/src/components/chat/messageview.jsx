import React from 'react';

import ListItem from '@material-ui/core/ListItem';
import ListItemText from '@material-ui/core/ListItemText';
import WarningIcon from '@material-ui/icons/Warning';
import CheckIcon from '@material-ui/icons/Check'
import Typography from '@material-ui/core/Typography';
import IconButton from '@material-ui/core/IconButton'

class MessageView extends React.Component {
    render() {
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
                <IconButton style={{marginRight:8}}>
                    <WarningIcon color="error"/>
                </IconButton>
                <IconButton>
                    <CheckIcon style={{color: '#2E7D32'}}/>
                </IconButton>
            </ListItem>
        )
    }
}

export default MessageView