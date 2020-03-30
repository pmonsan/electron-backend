export interface IActivityArea {
  id?: number;
  code?: string;
  label?: string;
  active?: boolean;
}

export class ActivityArea implements IActivityArea {
  constructor(public id?: number, public code?: string, public label?: string, public active?: boolean) {
    this.active = this.active || false;
  }
}
