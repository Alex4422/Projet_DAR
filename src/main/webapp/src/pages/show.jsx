import React from 'react';
import Typography from '@material-ui/core/Typography';
import {withRouter} from 'react-router-dom'
import {SERVER_URL} from "./app";

var request;

const rootStyle = {
    display: 'flex',
    flexDirection: 'column',
    flexGrow: 1,
};

const titleStyle = {
    background: 'linear-gradient(to top, rgba(0,0,0,1.0) 0%, rgba(0,0,0,0.5) 80%, rgba(0,0,0,0) 100%)',
    padding: 26,
}

class ShowDetailsPage extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            name: "",
            backdrop: "",
        }
    }
    render() {
        return (
            <div style={rootStyle}>
                <div style={this.titleBackgroundStyle()}>
                    <div style={titleStyle}>
                        <Typography variant="h2">
                            {this.state.name}
                        </Typography>
                        <Typography variant="h6" color="textSecondary" style={{maxWidth: 1000}}>
                            {this.state.overview}
                        </Typography>
                    </div>
                </div>
            </div>
        );
    }

    titleBackgroundStyle() {
        return {
            display: 'flex',
            flexDirection: 'column',
            justifyContent: 'flex-end',
            height: 400,
            backgroundSize: 'cover',
            backgroundImage: "url(https://image.tmdb.org/t/p/w1280/" + this.state.backdrop + ")",
        }
    }

    componentDidMount() {
        this.fetchData()
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
                overview: result.overview
            })
        }
    }
}

export default withRouter(ShowDetailsPage)