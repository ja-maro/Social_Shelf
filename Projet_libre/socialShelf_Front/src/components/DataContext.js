import { createContext } from "react";

export let DataContext = createContext({
    isLog: false,
    setIsLog: (isLog) => {},
    isAdmin: false,
    setIsAdmin: (isAdmin) => {},
    playerId: 0,
    setPlayerId: (playerId) => {},
});
