import React from 'react';
import {SERVER_URL} from "../pages/app";
import Typography from '@material-ui/core/Typography';
import List from '@material-ui/core/List';
import ListItem from '@material-ui/core/ListItem';
import ListItemIcon from '@material-ui/core/ListItemIcon';
import ListItemText from '@material-ui/core/ListItemText';
import Checkbox from '@material-ui/core/Checkbox';
import ExpandLess from '@material-ui/icons/ExpandLess';
import ExpandMore from '@material-ui/icons/ExpandMore';
import Tv from '@material-ui/icons/Tv';
import LiveTv from '@material-ui/icons/LiveTv';
import InfoOutlined from '@material-ui/icons/InfoOutlined';
import Collapse from '@material-ui/core/Collapse';
import CircularProgress from "@material-ui/core/CircularProgress/CircularProgress";


class SeasonDetails extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            openSeason: false,
            openEp: false,
            episodes: [],
        }
    }

    render() {
        return (

            <List component="div" disablePadding>
                <ListItem button onClick={this.handleClickSeason.bind(this)}>
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
                        {this.renderEpisodes()}
                    </Collapse>
                </div>
            </List>

        );
    }

    renderEpisodes(){
        if (this.state.episodes.length === 0) {
            return (<CircularProgress/>)
        } else {
            return (

                <List component="div" disablePadding>
                    {this.state.episodes.map(ep => {
                        return (

                            <ListItem button onClick={this.handleClickEp.bind(this)}>
                                <ListItemIcon style={{color: '#000'}}>
                                    <LiveTv />
                                </ListItemIcon>
                                <ListItemText inset primary={
                                    <Typography style={this.listTitleEpStyle()}>{ep.name}</Typography>
                                }/>
                                {this.state.openEp ? <ExpandLess /> : <ExpandMore />}
                                <div style={this.nestedEpDescrStyle()}>
                                    <Collapse in={this.state.openEp} timeout="auto" unmountOnExit>
                                        <List component="div" disablePadding>
                                            <ListItem button>
                                                <ListItemIcon style={{color: '#000'}}>
                                                    <InfoOutlined />
                                                </ListItemIcon>
                                                <ListItemText inset primary={
                                                    <Typography style={this.listTitleDescrStyle()}>{ep.overview}</Typography>
                                                }/>
                                            </ListItem>
                                        </List>
                                    </Collapse>
                                </div>
                            </ListItem>

                        );
                    })}
                </List>

            );
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

    nestedEpDescrStyle() {
        return {
            paddingLeft: '11%',
        }
    }

    nestedEpsStyle() {
        return {
            paddingLeft: '8%',
        }
    }


    handleClickSeason() {
        this.setState({openSeason: !this.state.openSeason});
        if (this.state.episodes.length === 0) {
            this.fetchData()
        }
    }

    handleClickEp() {
        this.setState({openEp: !this.state.openEp});
        if (this.state.episodes.length === 0) {
            this.fetchData()
        }
    }

    fetchData() {
        fetch(SERVER_URL + "/seasonDetails?showId=" + this.props.showId + "&seasonNumber=" + this.props.seasonNumber)
            .then(resp => {
                if (resp.status !== 400) {
                    return resp.json()
                }
            })
            .then(result => {
                this.setState({episodes: result.episodes})
            })
    }

}

export default SeasonDetails