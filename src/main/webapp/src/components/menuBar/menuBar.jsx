import React from 'react';
import {withRouter} from 'react-router-dom'

import Button from '@material-ui/core/Button'
import AppBar from '@material-ui/core/AppBar';
import Toolbar from '@material-ui/core/Toolbar';
import Typography from '@material-ui/core/Typography';
import IconButton from '@material-ui/core/IconButton';
import Home from '@material-ui/icons/Home'

import {AppContext} from '../../pages/app';
import {USER_TOKEN} from '../../globals'
import {LoginDialog, SignupDialog} from "./user_dialogs";

class MenuBar extends React.Component {
    render() {
        let labelStyle = {
            flexGrow: 1
        }

        return (
            <AppBar position="static" color="default">
                <Toolbar>
                    <IconButton onClick={() => this.props.history.push('/')}>
                        <Home/>
                    </IconButton>
                    <Typography style={labelStyle} variant="h6">
                        SuperSeries
                    </Typography>
                    <Button onClick={() => { this.loginDialog.openDialog() }}>
                        Login
                    </Button>
                    <Button onClick={() => { this.signupDialog.openDialog() }}>
                        Sign Up
                    </Button>
                </Toolbar>
                <LoginDialog onRef={(ref) => this.loginDialog = ref}/>
                <SignupDialog onRef={(ref) => this.signupDialog = ref}/>
            </AppBar>
        );
    }
}

const RoutedMenuBar = withRouter(MenuBar);

export default RoutedMenuBar;