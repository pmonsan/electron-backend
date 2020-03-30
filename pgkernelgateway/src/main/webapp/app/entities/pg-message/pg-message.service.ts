import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IPgMessage } from 'app/shared/model/pg-message.model';

type EntityResponseType = HttpResponse<IPgMessage>;
type EntityArrayResponseType = HttpResponse<IPgMessage[]>;

@Injectable({ providedIn: 'root' })
export class PgMessageService {
  public resourceUrl = SERVER_API_URL + 'api/pg-messages';

  constructor(protected http: HttpClient) {}

  create(pgMessage: IPgMessage): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(pgMessage);
    return this.http
      .post<IPgMessage>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(pgMessage: IPgMessage): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(pgMessage);
    return this.http
      .put<IPgMessage>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IPgMessage>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IPgMessage[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(pgMessage: IPgMessage): IPgMessage {
    const copy: IPgMessage = Object.assign({}, pgMessage, {
      messageDate: pgMessage.messageDate != null && pgMessage.messageDate.isValid() ? pgMessage.messageDate.toJSON() : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.messageDate = res.body.messageDate != null ? moment(res.body.messageDate) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((pgMessage: IPgMessage) => {
        pgMessage.messageDate = pgMessage.messageDate != null ? moment(pgMessage.messageDate) : null;
      });
    }
    return res;
  }
}
