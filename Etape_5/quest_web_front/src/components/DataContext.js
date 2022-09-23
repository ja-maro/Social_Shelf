import { createContext } from "react";

export const DataContext = createContext({
    isLog: false,
    setIsLog: (isLog) => {},
});
