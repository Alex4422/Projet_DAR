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
var forge = require('node-forge');

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
       // this.fetchData()


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

    fetchData() {
        this.setState({userName: []})
        this.setState({password: []})
        request = new XMLHttpRequest();
        //request.open("GET", SERVER_URL + "/search/show?searchValue=" + this.props.match.params.searchValue, true);


        //request.open("GET", SERVER_URL + "/login?username=" + this.props.match.userName +"&password=" + this.props.match.params.password, true);

        var password = password.sha256.create();

        request.send(null);
        request.addEventListener("readystatechange", this.processRequest, false);
    }

    processRequest() {
        if (request.readyState === 4 && request.status === 200) {
            let result = JSON.parse(request.responseText);
            this.setState({shows: result.results})
        }
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

    async submit() {
        //let api = `${SERVER_URL}/users?username=${this.props.userName}&password=${this.props.password}`;
        let api = `${SERVER_URL}/users?username=john&password=doe`;
        let user = await fetch(api, {method: 'post'}).catch(err => console.log(err));
        //let user = await fetch(login)
        console.log(user);
        /*request = new XMLHttpRequest();
        request.open("POST", api, true);
        request.send(null);*/
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
                    </div>
                </DialogContent>
                <DialogActions>
                    <Button onClick={() => { /*this.checkFields() &&*/ this.submit() }}>
                        Sign Up
                    </Button>
                </DialogActions>
            </Dialog>
        );
    }

}

export {LoginDialog, SignupDialog}