import React from 'react'
import Dialog from '@material-ui/core/Dialog'
import DialogTitle from '@material-ui/core/DialogTitle'
import DialogContent from '@material-ui/core/DialogContent'
import DialogActions from '@material-ui/core/DialogActions'
import FormControl from '@material-ui/core/FormControl'
import FormHelperText from '@material-ui/core/FormHelperText'
import Input from '@material-ui/core/Input'
import InputLabel from '@material-ui/core/InputLabel'
import Button from '@material-ui/core/Button'
import {SERVER_URL} from "../../pages/app";

const dialogDivStyle = {
    display: "flex",
    flexDirection: "column",
    minWidth: 300,
}

const textFieldStyle = {
    marginBottom: 8,
}

var request;

class UserDialogBase extends React.Component {
    constructor(props) {
        super(props)
        this.reset()
    }

    state = {
        open: false
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
}

class LoginDialog extends UserDialogBase {
    constructor(props) {
        super(props)
        this.reset()
    }

    reset() {
        this.setState({
            userName: '',
            userNameHelper: '',
            password: '',
            passwordHelper: '',
        })
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
                </DialogContent>
                <DialogActions>
                    <Button onClick={() => {this.checkFields()}}>
                        Login
                    </Button>
                </DialogActions>
            </Dialog>
        );
    }
}

class SignupDialog extends UserDialogBase {
    reset() {
        this.setState({
            firstName: '',
            firstNameHelper: '',
            lastName: '',
            lastNameHelper: '',
            userName: '',
            userNameHelper: '',
            password: '',
            passwordHelper: '',
        })
    }

    handleFirstNameChange = event => {
        this.setState({firstName: event.target.value})
    };

    handleLastNameChange = event => {
        this.setState({lastName: event.target.value})
    }

    handleUsernameChange = event => {
        this.setState({userName: event.target.value})
    }

    handlePasswordChange = event => {
        this.setState({password: event.target.value})
    }

    checkFields() {
        if (this.state.firstName === "") {
            this.setState({firstNameHelper: "The first name cannot be empty"})
        } else {
            this.setState({firstNameHelper: ""})
        }

        if (this.state.lastName === "") {
            this.setState({lastNameHelper: "The last name cannot be empty"})
        } else {
            this.setState({lastNameHelper: ""})
        }

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

        return this.state.firstNameHelper === '' &&
            this.state.lastNameHelper === '' &&
            this.state.userNameHelper === '' &&
            this.state.passwordHelper === ''
    }

    render() {
        return (
            <Dialog open={this.state.open}
                    onClose={this.closeDialog.bind(this)}>
                <DialogTitle>Sign Up</DialogTitle>
                <DialogContent>
                    <div style={dialogDivStyle}>
                        <FormControl error={this.state.firstNameHelper !== ''}>
                            <InputLabel>First Name</InputLabel>
                            <Input onChange={this.handleFirstNameChange}/>
                            <FormHelperText id="component-error-text">{this.state.firstNameHelper}</FormHelperText>
                        </FormControl>
                        <FormControl error={this.state.lastNameHelper !== ""}>
                            <InputLabel>Last Name</InputLabel>
                            <Input onChange={this.handleLastNameChange}/>
                            <FormHelperText id="component-error-text">{this.state.lastNameHelper}</FormHelperText>
                        </FormControl>
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

                        var md = forge.md.sha256.create();
                        md.update(password);
                        console.log(md.digest().toHex());
                    </div>
                </DialogContent>
                <DialogActions>
                    <Button onClick={() => { this.checkFields() }}>
                        Sign Up
                    </Button>
                </DialogActions>
            </Dialog>
        );
    }

    fetchData() {
        this.setState({shows: []})
        request = new XMLHttpRequest();
        request.open("GET", SERVER_URL + "/search/show?searchValue=" + this.props.match.params.searchValue, true);
        request.send(null);
        request.addEventListener("readystatechange", this.processRequest, false);
    }




}

export {LoginDialog, SignupDialog}