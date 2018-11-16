import React from 'react'

import List from '@material-ui/core/List';
import ListItem from '@material-ui/core/ListItem';
import ListItemText from '@material-ui/core/ListItemText';
import ExpandLess from '@material-ui/icons/ExpandLess';
import ExpandMore from '@material-ui/icons/ExpandMore';
import Collapse from '@material-ui/core/Collapse';
import CircularProgress from '@material-ui/core/CircularProgress';
import Typography from '@material-ui/core/Typography';
import Checkbox from '@material-ui/core/Checkbox';
import {SERVER_URL} from "../../pages/app";

class SeasonView extends React.Component {
    state = {
        open: false,
        episodes: [],
    };

    constructor(props) {
        super(props);
    }

    handleClick() {
        this.setState({open: !this.state.open});
        if (this.state.episodes.length === 0) {
            this.fetchData()
        }
    }

    fetchData() {
        let params = "showId=" + this.props.showId + "&seasonNumber=" + this.props.seasonNumber;
        if (this.props.context.userToken !== "") {
            params += "&userToken=" + this.props.context.userToken;
        }

        fetch(SERVER_URL + "/seasonDetails?" + params)
            .then(resp => {
                if (resp.status !== 400) {
                    return resp.json()
                }
            })
            .then(result => {
                this.setState({episodes: result.episodes})
            })
    }

    handleEpisodeChecked(episode) {
        if (episode.watched == null) {
            return
        }
        episode.watched = !episode.watched
        this.setState({episodes: this.state.episodes})
        this.updateEpisodeWatch(episode)
    }

    updateEpisodeWatch(episode) {
        const url = SERVER_URL + "/auth/userWatch";
        const params = "userToken=" + this.props.context.userToken +
            "&showId=" + this.props.showId +
            "&seasonNumber=" + this.props.seasonNumber +
            "&episodeId=" + episode.id;
        const method = episode.watched ? 'POST' : 'DELETE';
        fetch(url, {
            method: method,
            body: params,
            headers: {'Content-type': "application/x-www-form-urlencoded; charset=UTF-8"}
        })
            .then(response => {
                if (response.status === 200) {
                    return response.json()
                }
            })
            .then(result => {
                console.log(result)
            })
    }

    render() {
        return ([
            <ListItem button onClick={this.handleClick.bind(this)}>
                <ListItemText primary={
                    <Typography component="h2"
                                style={{fontSize: '150%'}}>{"Season " + this.props.seasonNumber}</Typography>
                }/>
                {this.state.open ? <ExpandLess/> : <ExpandMore/>}
            </ListItem>,
            <Collapse in={this.state.open} timeout="auto" unmountOnExit>
                {this.renderEpisodes()}
            </Collapse>
        ]);
    }

    renderEpisodes() {
        if (this.state.episodes.length === 0) {
            return (<CircularProgress/>)
        } else {
            return (
                <List>
                    {this.state.episodes.map(e => {
                        const checkBoxVisible = this.props.context.userToken !== "" ? 'visible' : 'hidden';
                        return (
                            <ListItem button>
                                <Checkbox theme="light" style={{visibility: checkBoxVisible}}
                                          tabIndex={-1}
                                          checked={e.watched}
                                          onClick={() => this.handleEpisodeChecked(e)}
                                />
                                <ListItemText
                                    primary={
                                        <Typography color="textPrimary">{e.episode_number} - {e.name}</Typography>
                                    }
                                    secondary={
                                        <Typography color="textSecondary">{e.overview}</Typography>
                                    }/>
                            </ListItem>
                        )
                    })}
                </List>
            )
        }
    }
}

export default SeasonView;