import { Injectable } from "@angular/core";


@Injectable()
export class PersistanceServoce {
  set(key: string, data: any): void {
    try {
      localStorage.setItem(key,JSON.stringify(data));
    } catch (e) {
      console.log('Error saving to localStorage', e);
    }
  }

  get(key: string) : any {
    try {
      return JSON.parse(localStorage.getItem(key));
    } catch (e) {
      console.log('Error while getting data to localStorage', e);
    }
  }

  remove(key: string) : void {
    try {
      localStorage.removeItem(key)
    } catch (e) {
      console.log('Error while removing data to localStorage', e);
    }
  }
}
