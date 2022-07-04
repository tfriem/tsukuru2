package de.tfriem.tsukuru.games.igdb;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.api.igdb.apicalypse.APICalypse;
import com.api.igdb.exceptions.RequestException;
import com.api.igdb.request.IGDBWrapper;
import com.api.igdb.request.ProtoRequestKt;
import com.api.igdb.request.TwitchAuthenticator;
import com.api.igdb.utils.TwitchToken;

import de.tfriem.tsukuru.games.config.TwitchCredentials;
import proto.Game;

@Service
public class IgdbServiceImpl implements IgdbService {
  private Logger logger = LoggerFactory.getLogger(IgdbServiceImpl.class);

  private final IGDBWrapper wrapper;

  public IgdbServiceImpl(TwitchCredentials twitchCredentials) {
    TwitchAuthenticator tAuth = TwitchAuthenticator.INSTANCE;
    TwitchToken token = tAuth.requestTwitchToken(twitchCredentials.getClientId(), twitchCredentials.getClientSecret());
    wrapper = IGDBWrapper.INSTANCE;
    wrapper.setCredentials(twitchCredentials.getClientId(), token.getAccess_token());
  }

  @Override
  public Game fetchGameByIgdbId(long igdbId) {
    try {
      final var games = ProtoRequestKt.games(IGDBWrapper.INSTANCE,
          new APICalypse().fields("*,genres.*,cover.*,websites.*,platforms.*,release_dates.*").where("id = " + igdbId));
      if (games.size() == 1) {
        return games.get(0);
      }
    } catch (RequestException e) {
      logger.error("Error requesting game from igdb", e);
    }
    return null;
  }

  @PostConstruct
  public void init() {
    Game g = fetchGameByIgdbId(123);

    System.out.println(g.getName());

  }
}
