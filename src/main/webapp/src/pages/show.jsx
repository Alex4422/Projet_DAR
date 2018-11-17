import React from 'react';
import {withRouter} from 'react-router-dom';

import StarRatingComponent from 'react-star-rating-component';

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
            averageRating: null,
            userRating: null,
        }
    }

    rateShow(userToken, rating) {
        this.setState({userRating: rating});
        const url = SERVER_URL + "/auth/rating";
        const params = "userToken=" + userToken +
            "&showId=" + this.props.match.params.id +
            "&rating=" + rating;
        fetch(url, {
            method: 'POST',
            body: params,
            headers: {'Content-type': "application/x-www-form-urlencoded; charset=UTF-8"}
        });
        this.fetchData();
    }

    renderRatingArea() {
        return (
            <AppContext>
                {ctx => {
                    if (ctx.userToken !== "") {
                        return (
                            <StarRatingComponent
                                starCount={10}
                                value={this.state.userRating}
                                starColor="#0277BD"
                                emptyStarColor="#888"
                                onStarClick={(function(next, prev, name) {this.rateShow(ctx.userToken, next)}).bind(this)}/>
                        )
                    }
                }}
            </AppContext>
        )
    }

    renderAverageRating() {
        if (this.state.averageRating != null) {
            return (
                <Typography variant="h5" style={{color: '#FFF'}}>
                    Average rating: {this.state.averageRating}
                </Typography>
            )
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
                        {this.renderRatingArea()}
                        {this.renderAverageRating()}
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
        let params = "id=" + this.props.match.params.id;
        if (this.props.context.userToken !== "") {
            params += "&userToken=" + this.props.context.userToken
        }
        request.open("GET", SERVER_URL + "/show?" + params, true);
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
                averageRating: result.average_rating,
                userRating: result.rating,
            })
        }
    }
}

export default withRouter(ShowDetailsPage)