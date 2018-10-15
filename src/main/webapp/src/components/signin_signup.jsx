import React from 'react'
import Button from "@material-ui/core/Button/Button";
import TextField from '@material-ui/core/TextField'

class SignInSignUp extends React.Component {
    render() {
        let buttonStyle = {
            width: 110,
            height: 50,
            margin: 16,
        };

        return ([
            <Button variant="contained" color="primary" style={buttonStyle}>
                Sign in
            </Button>,
            <Button variant="contained" color="primary" style={buttonStyle}
                    onClick={this.props.onSignUp}>
                Sign up
            </Button>]);
    }
}

class SignUp extends React.Component {
    render() {
        let divStyle = {
            display: 'flex',
            flexDirection: 'column',
            justifyContent: 'space-between',
            flexGrow: 1,
            margin: 16,
            height: 340,
        };

        let buttonStyle = {
            margin: 16,
        }

        return (
            <div style={divStyle}>
                <TextField variant="outlined" label="First Name"/>
                <TextField variant="outlined" label="Last Name"/>
                <TextField variant="outlined" label="Username"/>
                <TextField variant="outlined" label="PassWord" type="password"/>
                <Button style={buttonStyle} variant="contained" color="primary">
                    Create Account
                </Button>
            </div>
        );
    }
}

export { SignInSignUp, SignUp }

