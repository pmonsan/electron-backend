import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IForex } from 'app/shared/model/forex.model';

type EntityResponseType = HttpResponse<IForex>;
type EntityArrayResponseType = HttpResponse<IForex[]>;

@Injectable({ providedIn: 'root' })
export class ForexService {
  public resourceUrl = SERVER_API_URL + 'api/forexes';

  constructor(protected http: HttpClient) {}

  create(forex: IForex): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(forex);
    return this.http
      .post<IForex>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(forex: IForex): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(forex);
    return this.http
      .put<IForex>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IForex>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IForex[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(forex: IForex): IForex {
    const copy: IForex = Object.assign({}, forex, {
      creationDate: forex.creationDate != null && forex.creationDate.isValid() ? forex.creationDate.toJSON() : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.creationDate = res.body.creationDate != null ? moment(res.body.creationDate) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((forex: IForex) => {
        forex.creationDate = forex.creationDate != null ? moment(forex.creationDate) : null;
      });
    }
    return res;
  }
}
