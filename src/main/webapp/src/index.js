import React from 'react';
import ReactDOM from 'react-dom';
import { createMuiTheme, MuiThemeProvider } from '@material-ui/core/styles'
import blue from '@material-ui/core/colors/blue'
import indigo from '@material-ui/core/colors/indigo'
import './index.css';
import {App} from "./pages/app";

const theme = createMuiTheme({
    palette: {
        type: 'dark',
        primary: blue,
        secondary: blue,
    }
})

ReactDOM.render(
    <MuiThemeProvider theme={theme}>
        <App/>
    </MuiThemeProvider>,
    document.getElementById('root'));
