import React from 'react'

import AccountCircle from '@material-ui/icons/AccountCircle';
import IconButton from '@material-ui/core/IconButton';
import MenuItem from '@material-ui/core/MenuItem';
import Menu from '@material-ui/core/Menu';

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
                <MenuItem onClick={this.closeMenu.bind(this)}>Logout</MenuItem>
            </Menu>
        )
    }

    openMenu(event) {
        this.setState({menuAnchor: event.currentTarget})
    }

    closeMenu() {
        this.setState({menuAnchor: null})
    }
}

export default AccountView