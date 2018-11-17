import React from 'react'

import AccountCircle from '@material-ui/icons/AccountCircle';
import IconButton from '@material-ui/core/IconButton';
import MenuItem from '@material-ui/core/MenuItem';
import Menu from '@material-ui/core/Menu';
import {SERVER_URL} from "../../pages/app";

class AccountView extends React.Component {
    state = {
        menuAnchor: null,
    };

    render() {
        let menu = this.renderMenu(this.state.menuAnchor);
        return ([
            <IconButton onClick={this.openMenu.bind(this)}
                        aria-owns={this.state.menuAnchor ? 'menu' : null}
                        aria-haspopup="true">
                <AccountCircle/>
            </IconButton>,
            menu
        ]);
    }

    renderMenu(anchor) {
        return (
            <Menu id='menu' anchorEl={anchor}
                  anchorOrigin={{ vertical: 'top', horizontal: 'right' }}
                  transformOrigin={{ vertical: 'top', horizontal: 'right' }}
                  open={anchor != null}
                  onClose={this.closeMenu.bind(this)}>
                <MenuItem onClick={this.closeMenu.bind(this)}>Profile</MenuItem>
                <MenuItem onClick={this.handleLogoutClick.bind(this)}>Logout</MenuItem>
            </Menu>
        )
    }

    handleLogoutClick() {
        this.closeMenu();
        this.logout();
    }

    logout() {
        const url = SERVER_URL + "/auth/logout";
        const params = "userToken=" + this.props.context.userToken
        fetch(url, {
            method: 'POST',
            body: params,
            headers: {'Content-type': "application/x-www-form-urlencoded; charset=UTF-8"}
        });
        this.props.context.setUserToken("")
    }

    openMenu(event) {
        this.setState({menuAnchor: event.currentTarget})
    }

    closeMenu() {
        this.setState({menuAnchor: null})
    }
}

export default AccountView