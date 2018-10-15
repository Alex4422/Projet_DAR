import React from 'react';
import DefaultPage from './default'
import SPA from "./spa";

class MainPage extends React.Component {
    constructor(props, context) {
        super(props, context)
        this.state = {
            loggedIn: false,
        }
    }

    render() {
        if (this.state.loggedIn) {
            return <SPA/>;
        } else {
            return <DefaultPage/>;
        }
    }
}

export default MainPage;