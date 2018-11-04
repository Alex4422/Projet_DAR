import React from 'react';
import {Route, NavLink, HashRouter} from "react-router-dom";

import HomePage from './home';
import RoutedMenuBar from '../components/menuBar/menuBar';
import SearchPage from "./search";

const AppContext = React.createContext();

const SERVER_URL = "http://localhost:8080/api/v1";

var context = {
    "userToken": "",
    "setUserToken": ""
};

class App extends React.Component {
    state = {
        userToken: "",
        setUserToken: (token) => {this.setState({userToken: token})}
    };

    render() {
        let divStyle = {
            display: 'flex',
            height: '93vh',
            marginTop: 2,
        };
        return (
            <AppContext.Provider value={this.state}>
                <HashRouter>
                    <div>
                        <RoutedMenuBar style={{position: 'fixed', top:0, marginBottom: 2}}/>
                        <div className="content" style={divStyle}>
                            <Route exact path="/" component={HomePage}/>
                            <Route path="/search/:searchValue" component={SearchPage}/>
                        </div>
                    </div>
                </HashRouter>
            </AppContext.Provider>
        )
    }
}

export {App, AppContext, SERVER_URL};