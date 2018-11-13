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

var request;

class SeasonDetails extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            name: "",
            openSeasons: false,
            openSeason: false,
            openEps: false,
            openEp: false,
        }
    }

    render() {
        return (
            <div style={this.listStyle()}>
                <List component="nav">
                    <ListItem button onClick={() => {
                        this.setState(state => ({ openSeasons: !this.state.openSeasons }))}
                    }>
                        <ListItemIcon style={{color: '#000'}}>
                            <Tv />
                        </ListItemIcon>
                        <ListItemText inset primary={
                            <Typography style={this.listTitleSeasonStyle()}>Seasons</Typography>
                        }/>
                        {this.state.openSeasons ? <ExpandLess /> : <ExpandMore />}
                        </ListItem>

                    <div style={this.nestedSeasonStyle()}>
                        <Collapse in={this.state.openSeasons} timeout="auto" unmountOnExit>
                            <List component="div" disablePadding>
                                <ListItem button onClick={() => {
                                    this.setState(state => ({ openSeason: !this.state.openSeason }))}
                                }>
                                    <ListItemIcon style={{color: '#000'}}>
                                        <Tv />
                                    </ListItemIcon>
                                    <ListItemText inset primary={
                                        <Typography style={this.listTitleSeasonStyle()}>Season 1</Typography>
                                    }/>
                                    {this.state.openSeason ? <ExpandLess /> : <ExpandMore />}
                                    </ListItem>

                                <div style={this.nestedEpsStyle()}>
                                    <Collapse in={this.state.openSeason} timeout="auto" unmountOnExit>
                                        <List component="div" disablePadding>
                                            <ListItem button onClick={() => {
                                                this.setState(state => ({ openEps: !this.state.openEps }))}
                                            }>
                                                <ListItemIcon style={{color: '#000'}}>
                                                    <LiveTv />
                                                </ListItemIcon>
                                                <ListItemText inset primary={
                                                    <Typography style={this.listTitleEpStyle()}>Episodes</Typography>
                                                }/>
                                                {this.state.openEps ? <ExpandLess /> : <ExpandMore />}
                                                </ListItem>

                                            <div style={this.nestedEpStyle()}>
                                                <Collapse in={this.state.openEps} timeout="auto" unmountOnExit>
                                                    <List component="div" disablePadding>
                                                        <ListItem button onClick={() => {
                                                            this.setState(state => ({ openEp: !this.state.openEp }))}
                                                        }>
                                                            <ListItemIcon style={{color: '#000'}}>
                                                                <LiveTv />
                                                            </ListItemIcon>
                                                            <ListItemText inset primary={
                                                                <Typography style={this.listTitleEpStyle()}>Episode 1</Typography>
                                                            }/>
                                                            {this.state.openEp ? <ExpandLess /> : <ExpandMore />}
                                                            </ListItem>

                                                        <div style={this.nestedEpDescrStyle()}>
                                                            <Collapse in={this.state.openEp} timeout="auto" unmountOnExit>
                                                                <List component="div" disablePadding>
                                                                    <ListItem button>
                                                                        <ListItemIcon style={{color: '#000'}}>
                                                                            <InfoOutlined />
                                                                        </ListItemIcon>
                                                                        <ListItemText inset primary={
                                                                            <Typography style={this.listTitleDescrStyle()}>Description</Typography>
                                                                        }/>
                                                                    </ListItem>

                                                                </List>
                                                            </Collapse>
                                                        </div>
                                                    </List>
                                                </Collapse>
                                            </div>
                                        </List>
                                    </Collapse>
                                </div>
                            </List>
                        </Collapse>
                    </div>
                </List>
            </div>
        );
    }

    listStyle() {
        return {
            display: 'flex',
            flexDirection: 'row',
        }
    }

    listTitleSeasonStyle() {
        return {
            color: '#000',
            fontSize: '150%',
        }
    }

    listTitleEpStyle() {
        return {
            color: '#000',
            fontSize: '120%',
        }
    }

    listTitleDescrStyle() {
        return {
            color: '#000',
            fontSize: '100%',
        }
    }

    nestedSeasonStyle() {
        return {
            paddingLeft: '5%',
        }
    }

    nestedEpsStyle() {
        return {
            paddingLeft: '8%',
        }
    }

    nestedEpStyle() {
        return {
            paddingLeft: '11%',
        }
    }

    nestedEpDescrStyle() {
        return {
            paddingLeft: '14%',
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