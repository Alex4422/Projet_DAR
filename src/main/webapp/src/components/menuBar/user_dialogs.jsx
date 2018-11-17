import React from 'react'
import forge from 'node-forge'
import Dialog from '@material-ui/core/Dialog'
import DialogTitle from '@material-ui/core/DialogTitle'
import DialogContent from '@material-ui/core/DialogContent'
import DialogActions from '@material-ui/core/DialogActions'
import FormControl from '@material-ui/core/FormControl'
import FormHelperText from '@material-ui/core/FormHelperText'
import Input from '@material-ui/core/Input'
import InputLabel from '@material-ui/core/InputLabel'
import Button from '@material-ui/core/Button'
import CircularProgress from '@material-ui/core/CircularProgress'
import Typography from '@material-ui/core/Typography'
import {SERVER_URL} from "../../pages/app"



const dialogDivStyle = {
    display: "flex",
    flexDirection: "column",
    minWidth: 300,
}

const textFieldStyle = {
    marginBottom: 8,
}

class UserDialogBase extends React.Component {
    constructor(props) {
        super(props)
        this.reset()
    }

    state = {
        open: false,
        globalHelper: '',
    };

    openDialog() {
        this.setState({open: true})
        this.reset()
    }

    closeDialog() {
        this.setState({open: false})
    }

    componentDidMount() {
        this.props.onRef(this)
    }

    handleSubmit() {
        if (this.checkFields()) {
            this.submit()
        }
    }
}

class LoginDialog extends UserDialogBase {
    constructor(props) {
        super(props);
        this.reset();
        this.request = new XMLHttpRequest();
    }

    reset() {
        this.setState({
            userName: '',
            userNameHelper: '',
            password: '',
            passwordHelper: '',
            pendingRequest: false,
        })
    }

    handleSubmit() {
        if (this.checkFields()) {
            this.submit()
        }
    }

    checkFields() {
        if (this.state.userName === "") {
            this.setState({userNameHelper: "The username cannot be empty"})
        } else {
            this.setState({userNameHelper: ""})
        }

        if (this.state.password === "") {
            this.setState({passwordHelper: "The password cannot be empty"})
        } else {
            this.setState({passwordHelper: ""})
        }

        return this.state.userNameHelper === '' &&
            this.state.passwordHelper === ''
    }

    submit() {
        const digest = forge.md.sha256.create();
        digest.update(this.state.password);
        const hashedPassword = digest.digest().toHex();
        fetch(SERVER_URL + "/login?username=" + this.state.userName + "&password=" + hashedPassword)
            .then(resp => {
                if (resp.status === 400) {
                    this.setState({globalHelper: "Invalid login or password"})
                } else {
                    return resp.json()
                }
            })
            .then(result => {
                this.props.context.setUserToken(result.userToken)
                this.setState({open: false})
            })
            .catch(error => {
                console.log(error)
            })
    }


    render() {
        return (
            <Dialog open={this.state.open}
                    onClose={this.closeDialog.bind(this)}>
                <DialogTitle>Login</DialogTitle>
                <DialogContent>
                    <div style={dialogDivStyle}>
                        <FormControl error={this.state.userNameHelper !== ''}>
                            <InputLabel>Username</InputLabel>
                            <Input onChange={(event) => {this.setState({userName: event.target.value})}}/>
                            <FormHelperText id="component-error-text">{this.state.userNameHelper}</FormHelperText>
                        </FormControl>
                        <FormControl error={this.state.passwordHelper !== ""}>
                            <InputLabel>Password</InputLabel>
                            <Input type="password" onChange={(event) => {this.setState({password: event.target.value})}}/>
                            <FormHelperText id="component-error-text">{this.state.passwordHelper}</FormHelperText>
                        </FormControl>
                    </div>
                    <Typography color="error">
                        {this.state.globalHelper}
                    </Typography>
                </DialogContent>
                <DialogActions>
                    {this.getValidationView()}
                </DialogActions>
            </Dialog>
        );
    }

    getValidationView() {
        if (this.state.pendingRequest) {
            return(<CircularProgress/>);
        } else {
            return (
                <Button onClick={this.handleSubmit.bind(this)}>
                    Login
                </Button>
            )
        }
    }
}

class SignupDialog extends UserDialogBase {
    reset() {
        this.setState({
            userName: '',
            userNameHelper: '',
            password: '',
            passwordHelper: '',
        })
    }

    handleUsernameChange = event => {
        this.setState({userName: event.target.value})
    };

    handlePasswordChange = event => {
        this.setState({password: event.target.value})
    };

    checkFields() {
        if (this.state.userName === "") {
            this.setState({userNameHelper: "The username cannot be empty"})
        } else {
            this.setState({userNameHelper: ""})
        }

        if (this.state.password === "") {
            this.setState({passwordHelper: "The password cannot be empty"})
        } else {
            this.setState({passwordHelper: ""})
        }

        return this.state.userNameHelper === '' &&
            this.state.passwordHelper === ''
    }

    submit() {
        const digest = forge.md.sha256.create();
        digest.update(this.state.password);
        const hashedPassword = digest.digest().toHex();
        const params = "username=" + this.state.userName + "&password=" + hashedPassword
        fetch(SERVER_URL + "/users?" + params, {method: 'post'})
            .then(resp => {
                if (resp.status === 400) {
                    this.setState({globalHelper: "This username is already taken."})
                } else {
                    this.setState({open: false})
                }
            })
            .catch(error => {
                console.log(error)
            })
    }

    render() {
        return (
            <Dialog open={this.state.open}
                    onClose={this.closeDialog.bind(this)}>
                <DialogTitle>Sign Up</DialogTitle>
                <DialogContent>
                    <div style={dialogDivStyle}>
                        <FormControl error={this.state.userNameHelper !== ""}>
                            <InputLabel>Username</InputLabel>
                            <Input onChange={this.handleUsernameChange}/>
                            <FormHelperText id="component-error-text">{this.state.userNameHelper}</FormHelperText>
                        </FormControl>
                        <FormControl error={this.state.passwordHelper !== ""}>
                            <InputLabel>Password</InputLabel>
                            <Input type="password" onChange={this.handlePasswordChange}/>
                            <FormHelperText id="component-error-text">{this.state.passwordHelper}</FormHelperText>
                        </FormControl>
                    </div>
                    <Typography color="error">
                        {this.state.globalHelper}
                    </Typography>
                </DialogContent>
                <DialogActions>
                    <Button onClick={this.handleSubmit.bind(this)}>
                        Sign Up
                    </Button>
                </DialogActions>
            </Dialog>
        );
    }
}

export {LoginDialog, SignupDialog}