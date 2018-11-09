import React from 'react';
import {withRouter} from 'react-router-dom'
import CircularProgress from '@material-ui/core/CircularProgress';
import GridList from '@material-ui/core/GridList';
import GridListTile from '@material-ui/core/GridListTile';
import {SERVER_URL} from "./app";
import ShowCard from "../components/showCard";

const rootStyle = {
    display: 'flex',
    flexDirection: 'column',
    flexGrow: 1,
    justifyContent: 'center',
    alignItems: 'center',
};

const COL_WIDTH = 150;

var request;

class SearchPage extends React.Component {
    constructor(props) {
        super(props)
        this.state = {
            shows: []
        };
        this.processRequest = this.processRequest.bind(this)
    }

    render() {
        if (this.state.shows.length === 0) {
            return this.undefinedProgress()
        } else {
            return this.postersGrid()
        }
    }

    undefinedProgress() {
        return (
            <div style={rootStyle}>
                <CircularProgress/>
                <p>Searching shows matching: "{this.props.match.params.searchValue}" </p>
            </div>
        );
    }

    postersGrid() {
        return (
                <GridList style={{flex: 'auto', alignItems: 'flex-start'}} padding={40}>
                    {this.state.shows.map(show => (
                        <GridListTile style={{height: 330, maxWidth: 250}}>
                            <ShowCard poster={"https://image.tmdb.org/t/p/w500/" + show.poster_path}
                                      name={show.name}
                                      onClicked={() => {this.showClicked(show.id)}}/>
                        </GridListTile>
                    ))}
                </GridList>
        )
    }

    showClicked(showId) {
        this.props.history.push('/showPage/' + showId.toString())
    }

    componentDidMount() {
        this.fetchData()
    }

    componentDidUpdate(previousProps, previousState, snap) {
        if (previousProps.match.params.searchValue !== this.props.match.params.searchValue) {
            this.fetchData()
        }
    }

    fetchData() {
        this.setState({shows: []})
        request = new XMLHttpRequest();
        request.open("GET", SERVER_URL + "/search/show?searchValue=" + this.props.match.params.searchValue, true);
        request.send(null);
        request.addEventListener("readystatechange", this.processRequest, false);
    }

    processRequest() {
        if (request.readyState === 4 && request.status === 200) {
            let result = JSON.parse(request.responseText);
            this.setState({shows: result.results})
        }
    }
}

export default withRouter(SearchPage)