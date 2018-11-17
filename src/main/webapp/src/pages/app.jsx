import React from 'react';
import {Route, HashRouter} from "react-router-dom"

import {Cookies} from 'react-cookie'

import { createMuiTheme, MuiThemeProvider } from '@material-ui/core/styles'
import blue from '@material-ui/core/colors/blue'

import HomePage from './home'
import RoutedMenuBar from '../components/menuBar/menuBar'
import SearchPage from "./search"
import ShowDetailsPage from "./show"

const AppContext = React.createContext();

const SERVER_URL = "http://localhost:8080/api/v1";

const cookies = new Cookies();

const darkTheme = createMuiTheme({
    palette: {
        type: 'dark',
        primary: blue,
        secondary: blue,
    }
});

const lightheme = createMuiTheme({
    palette: {
        primary: blue,
        secondary: blue,
    }
});

class App extends React.Component {
    state = {
        userToken: cookies.get('userToken') || "",
        setUserToken: this.setUserToken.bind(this),
    };

    setUserToken(token) {
        this.setState({userToken: token});
        cookies.set('userToken', token, {path: '/'});
    }

    render() {
        let divStyle = {
            display: 'flex',
            height: '93vh',
        };
        return (
            <AppContext.Provider value={this.state}>
                <HashRouter>
                    <div>
                        <MuiThemeProvider theme={darkTheme}>
                            <AppContext.Consumer>
                                {ctx => {return (<RoutedMenuBar style={{position: 'fixed', top:0, marginBottom: 2}} context={ctx}/>)}}
                            </AppContext.Consumer>
                        </MuiThemeProvider>
                        <MuiThemeProvider theme={lightheme}>
                            <div className="content" style={divStyle}>
                                <Route exact path="/" component={HomePage}/>
                                <Route path="/search/:searchValue" component={SearchPage}/>
                                <Route path="/showPage/:id" component={ShowDetailsPage}/>
                            </div>
                        </MuiThemeProvider>
                    </div>
                </HashRouter>
            </AppContext.Provider>
        )
    }
}

export {App, AppContext, SERVER_URL};