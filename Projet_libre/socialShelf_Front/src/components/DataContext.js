import { createContext } from "react";

export let DataContext = createContext({
    isLog: false,
    setIsLog: (isLog) => {},
});
