import React from 'react';
import {Route, NavLink, HashRouter} from "react-router-dom";

import HomePage from './home';
import RoutedMenuBar from '../components/menuBar/menuBar';
import SearchPage from "./search";
import ShowDetailsPage from "./show";
import ProfilePage from './ProfilePage';

import {routerActions} from 'react-router-redux';
import {UserAuthWrapper} from 'redux-auth-wrapper';

const AppContext = React.createContext();

const SERVER_URL = "http://localhost:8080/api/v1";

var context = {
    "userToken": "",
    "setUserToken": ""
};

// Redirects to /login by default
const UserIsAuthenticated = UserAuthWrapper({
    authSelector: state => state.auth, // how to get the user state
    predicate: (auth) => auth.isAuthenticated, // function to run against the auth state to determine if authenticated
    redirectAction: routerActions.replace, // the redux action to dispatch for redirect
    wrapperDisplayName: 'UserIsAuthenticated' // a nice name for this auth check
});




class App extends React.Component {
    state = {
        userToken: "",
        setUserToken: (token) => {this.setState({userToken: token})}
    };

    render() {
        let divStyle = {
            display: 'flex',
            height: '93vh',
        };
        return (
            <AppContext.Provider value={this.state}>
                <HashRouter>
                    <div>
                        <RoutedMenuBar style={{position: 'fixed', top:0, marginBottom: 2}}/>
                        <div className="content" style={divStyle}>
                            <Route exact path="/" component={HomePage}/>
                            <Route path="/search/:searchValue" component={SearchPage}/>
                            <Route path="/showPage/:id" component={ShowDetailsPage}/>
                            <Route path="/profile" component={UserIsAuthenticated(ProfilePage)}/>
                        </div>
                    </div>
                </HashRouter>
            </AppContext.Provider>
        )
    }
}

export {App, AppContext, SERVER_URL};