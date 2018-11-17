import React from 'react';
import {withRouter} from 'react-router-dom'

import Typography from '@material-ui/core/Typography';
import Card from '@material-ui/core/Card';

import {SERVER_URL, AppContext} from "./app";
import SeasonView from "../components/seasons/seasonView";
import ChatView from "../components/chat/chatview";

var request;

const HEADER_HEIGHT = 400;

const rootStyle = {
    display: 'flex',
    flexDirection: 'column',
    flexGrow: 1,
};

const titleStyle = {
    background: 'linear-gradient(to top, rgba(0,0,0,1.0) 0%, rgba(0,0,0,0.5) 80%, rgba(0,0,0,0) 100%)',
    padding: 26,
};

const bodyStyle = {
    display: 'flex',
    height: '69%',
    flexDirection: 'row',
    position: 'relative',
    bottom: 0,
};

const cardsStyle = {
    overflow: 'auto',
    margin: 8,
    width: '100%',
};

class ShowDetailsPage extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            name: "",
            backdrop: "",
            seasons: [],
        }
    }

    render() {
        return (

            <div style={rootStyle}>
                <div style={this.titleBackgroundStyle()}>
                    <div style={titleStyle}>
                        <Typography variant="h2" style={{color: '#fff'}}>
                            {this.state.name}
                        </Typography>
                        <Typography variant="h6" style={{maxWidth: 1000, color: '#ccc'}}>
                            {this.state.overview}
                        </Typography>
                    </div>
                </div>
                <div style={bodyStyle}>
                    <AppContext.Consumer>
                        {ctx => {
                            return ([
                                <Card style={cardsStyle}>
                                    {this.state.seasons.map(season => {
                                        return (
                                            <SeasonView context={ctx} seasonNumber={season} showId={this.props.match.params.id}/>
                                        )
                                    })}
                                </Card>,
                                this.renderChat(ctx)
                            ])
                        }}
                    </AppContext.Consumer>
                </div>
            </div>

        );
    }

    renderChat(context) {
        if (context.userToken !== "") {
            return (
                <Card style={cardsStyle}>
                    <ChatView context={context} showId={this.props.match.params.id}/>
                </Card>
            );
        }
    }

    titleBackgroundStyle() {
        return {
            display: 'flex',
            flexDirection: 'column',
            justifyContent: 'flex-end',
            minHeight: HEADER_HEIGHT,
            backgroundSize: 'cover',
            backgroundImage: "url(https://image.tmdb.org/t/p/w1280/" + this.state.backdrop + ")",
        }
    }

    componentDidMount() {
        this.fetchData();
    }

    fetchData() {
        request = new XMLHttpRequest();
        request.open("GET", SERVER_URL + "/show?id=" + this.props.match.params.id, true);
        request.send(null);
        request.addEventListener("readystatechange", this.processRequest.bind(this), false);
    }

    processRequest() {
        if (request.readyState === 4 && request.status === 200) {
            let result = JSON.parse(request.responseText);
            this.setState({
                name: result.name,
                backdrop: result.backdrop_path,
                overview: result.overview,
                seasons: result.seasons,
            })
        }
    }

}

export default withRouter(ShowDetailsPage)