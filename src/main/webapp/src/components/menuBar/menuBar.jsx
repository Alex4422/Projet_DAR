import React from 'react';
import {withRouter} from 'react-router-dom'

import Button from '@material-ui/core/Button'
import AppBar from '@material-ui/core/AppBar';
import Toolbar from '@material-ui/core/Toolbar';
import Typography from '@material-ui/core/Typography';
import IconButton from '@material-ui/core/IconButton';
import Home from '@material-ui/icons/Home'

import {LoginDialog, SignupDialog} from "./user_dialogs";
import SearchField from "./search_field";
import AccountView from "./account_view";

class MenuBar extends React.Component {
    render() {
        let labelStyle = {
            marginLeft: 8,
            marginRight: 24
        };

        let grow = {
            flexGrow: 1
        };

        return (
            <AppBar position="static" color="default">
                <Toolbar>
                    <IconButton onClick={() => {this.props.history.push('/')}}>
                        <Home/>
                    </IconButton>
                    <Typography style={labelStyle} variant="h6">
                        SuperSeries
                    </Typography>
                    <SearchField onSearch={(search) => {this.props.history.push("/search/" + search)}}/>
                    <div style={grow}/>
                    {this.getUserArea()}
                </Toolbar>
                <LoginDialog context={this.props.context} onRef={(ref) => this.loginDialog = ref}/>
                <SignupDialog context={this.props.context} onRef={(ref) => this.signupDialog = ref}/>
            </AppBar>
        );
    }

    getUserArea() {
        if (this.props.context.userToken === "") {
            return ([
                <Button onClick={() => { this.loginDialog.openDialog() }}>
                    Login
                </Button>,
                <Button onClick={() => { this.signupDialog.openDialog() }}>
                    Sign Up
                </Button>
            ])
        } else {
            return (<AccountView context={this.props.context}/>)
        }
    }
}

const RoutedMenuBar = withRouter(MenuBar);

export default RoutedMenuBar;