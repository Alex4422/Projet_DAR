import React from 'react';
import {withRouter} from 'react-router-dom'
import Button from '@material-ui/core/Button'

class UserArea extends React.Component {
    render() {
        if (this.props.context.userToken === '') {
            return this.loginActionButons()
        } else {
            return this.userBadge()
        }
    }

    loginActionButons() {
        return ( [
            <Button onClick={this.props.onLogin}>
                Login
            </Button>,
            <Button onClick={this.props.onSignup}>
                Sign up
            </Button>
        ]);
    }

    userBadge() {
        return (
            <p>Hello world !</p>

        );
    }
}

export default withRouter(UserArea);