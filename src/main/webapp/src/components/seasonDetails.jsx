import React from 'react';
import Typography from '@material-ui/core/Typography';
import List from '@material-ui/core/List';
import ListItem from '@material-ui/core/ListItem';
import ListItemIcon from '@material-ui/core/ListItemIcon';
import ListItemText from '@material-ui/core/ListItemText';
import ExpandLess from '@material-ui/icons/ExpandLess';
import ExpandMore from '@material-ui/icons/ExpandMore';
import Tv from '@material-ui/icons/Tv';
import LiveTv from '@material-ui/icons/LiveTv';
import InfoOutlined from '@material-ui/icons/InfoOutlined';
import Collapse from '@material-ui/core/Collapse';
import Checkbox from '@material-ui/core/Checkbox';
import {SERVER_URL} from "../pages/app";
import EpisodeDetails from "./episodeDetails";

var request;

class SeasonDetails extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            name: "",
            openSeason: false,
            openEps: false,
            openEp: false,
        }
    }

    render() {
        return (

            <List component="div" disablePadding>
                <ListItem button onClick={() => {
                    this.setState(state => ({ openSeason: !this.state.openSeason }))}
                }>
                    <ListItemIcon style={{color: '#000'}}>
                        <Tv />
                    </ListItemIcon>
                    <ListItemText inset primary={
                        <Typography style={this.listTitleSeasonStyle()}>Season {this.props.seasonNumber}</Typography>
                    }/>
                    {this.state.openSeason ? <ExpandLess /> : <ExpandMore />}
                    </ListItem>

                <div style={this.nestedEpsStyle()}>
                    <Collapse in={this.state.openSeason} timeout="auto" unmountOnExit>
                        <EpisodeDetails/>
                    </Collapse>
                </div>
            </List>

        );
    }

    listTitleSeasonStyle() {
        return {
            color: '#000',
            fontSize: '150%',
        }
    }

    nestedEpsStyle() {
        return {
            paddingLeft: '8%',
        }
    }

/*
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
                overview: result.overview
            })
        }
    }
*/

}

export default SeasonDetails