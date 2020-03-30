import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { PgkernelgatewaySharedModule } from 'app/shared';
import {
  Pg8583StatusComponent,
  Pg8583StatusDetailComponent,
  Pg8583StatusUpdateComponent,
  Pg8583StatusDeletePopupComponent,
  Pg8583StatusDeleteDialogComponent,
  pg8583StatusRoute,
  pg8583StatusPopupRoute
} from './';

const ENTITY_STATES = [...pg8583StatusRoute, ...pg8583StatusPopupRoute];

@NgModule({
  imports: [PgkernelgatewaySharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    Pg8583StatusComponent,
    Pg8583StatusDetailComponent,
    Pg8583StatusUpdateComponent,
    Pg8583StatusDeleteDialogComponent,
    Pg8583StatusDeletePopupComponent
  ],
  entryComponents: [
    Pg8583StatusComponent,
    Pg8583StatusUpdateComponent,
    Pg8583StatusDeleteDialogComponent,
    Pg8583StatusDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PgkernelgatewayPg8583StatusModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
