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
import UserArea from "./user_area";
import {AppContext} from "../../pages/app";

class MenuBar extends React.Component {
    render() {
        return (
            <AppContext.Consumer>
                {ctx => {
                    return this.content(ctx)
                }}
            </AppContext.Consumer>

        );
    }

    content(ctx) {
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
                    <IconButton onClick={() => this.props.history.push('/')}>
                        <Home/>
                    </IconButton>
                    <Typography style={labelStyle} variant="h6">
                        SuperSeries
                    </Typography>
                    <SearchField onSearch={(search) => {
                        this.props.history.push("/search/" + search)
                    }}/>
                    <div style={grow}/>
                    <UserArea context={ctx} onLogin={() => this.loginDialog.openDialog()}
                              onSignup={() => this.signupDialog.openDialog()}/>
                </Toolbar>
                <LoginDialog context={ctx} onRef={(ref) => this.loginDialog = ref}/>
                <SignupDialog context={ctx} onRef={(ref) => this.signupDialog = ref}/>
            </AppBar>
        )
    }
}

const RoutedMenuBar = withRouter(MenuBar);

export default RoutedMenuBar;