import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { PgkernelgatewaySharedModule } from 'app/shared';
import {
  Pg8583RequestComponent,
  Pg8583RequestDetailComponent,
  Pg8583RequestUpdateComponent,
  Pg8583RequestDeletePopupComponent,
  Pg8583RequestDeleteDialogComponent,
  pg8583RequestRoute,
  pg8583RequestPopupRoute
} from './';

const ENTITY_STATES = [...pg8583RequestRoute, ...pg8583RequestPopupRoute];

@NgModule({
  imports: [PgkernelgatewaySharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    Pg8583RequestComponent,
    Pg8583RequestDetailComponent,
    Pg8583RequestUpdateComponent,
    Pg8583RequestDeleteDialogComponent,
    Pg8583RequestDeletePopupComponent
  ],
  entryComponents: [
    Pg8583RequestComponent,
    Pg8583RequestUpdateComponent,
    Pg8583RequestDeleteDialogComponent,
    Pg8583RequestDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PgkernelgatewayPg8583RequestModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
