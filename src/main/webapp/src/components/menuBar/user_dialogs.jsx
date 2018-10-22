import React from 'react'
import Dialog from '@material-ui/core/Dialog'
import DialogTitle from '@material-ui/core/DialogTitle'
import DialogContent from '@material-ui/core/DialogContent'
import DialogActions from '@material-ui/core/DialogActions'
import TextField from '@material-ui/core/TextField'
import Button from '@material-ui/core/Button'

const dialogDivStyle = {
    display: "flex",
    flexDirection: "column",
    minWidth: 300,
}

const textFieldStyle = {
    marginBottom: 8,
}

class UserDialogBase extends React.Component {
    state = {
        open: false
    };

    openDialog() {
        this.setState({open: true})
    }

    closeDialog() {
        this.setState({open: false})
    }

    componentDidMount() {
        this.props.onRef(this)
    }
}

class LoginDialog extends UserDialogBase {
    render() {
        return (
            <Dialog open={this.state.open}
                    onClose={this.closeDialog.bind(this)}>
                <DialogTitle>Login</DialogTitle>
                <DialogContent>
                    <div style={dialogDivStyle}>
                        <TextField label="UserName" style={textFieldStyle}/>
                        <TextField label="Password" style={textFieldStyle} type="password"/>
                    </div>
                </DialogContent>
                <DialogActions>
                    <Button>
                        Login
                    </Button>
                </DialogActions>
            </Dialog>
        );
    }
}

class SignupDialog extends UserDialogBase {
    render() {
        return (
            <Dialog open={this.state.open}
                    onClose={this.closeDialog.bind(this)}>
                <DialogTitle>Sign Up</DialogTitle>
                <DialogContent>
                    <div style={dialogDivStyle}>
                        <TextField label="First name" style={textFieldStyle}/>
                        <TextField label="Last name" style={textFieldStyle}/>
                        <TextField label="Username" style={textFieldStyle}/>
                        <TextField label="Password" style={textFieldStyle} type="password"/>
                    </div>
                </DialogContent>
                <DialogActions>
                    <Button>
                        Sign Up
                    </Button>
                </DialogActions>
            </Dialog>
        );
    }
}

export {LoginDialog, SignupDialog}