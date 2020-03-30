import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { PgkernelgatewaySharedModule } from 'app/shared';
import {
  Pg8583CallbackComponent,
  Pg8583CallbackDetailComponent,
  Pg8583CallbackUpdateComponent,
  Pg8583CallbackDeletePopupComponent,
  Pg8583CallbackDeleteDialogComponent,
  pg8583CallbackRoute,
  pg8583CallbackPopupRoute
} from './';

const ENTITY_STATES = [...pg8583CallbackRoute, ...pg8583CallbackPopupRoute];

@NgModule({
  imports: [PgkernelgatewaySharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    Pg8583CallbackComponent,
    Pg8583CallbackDetailComponent,
    Pg8583CallbackUpdateComponent,
    Pg8583CallbackDeleteDialogComponent,
    Pg8583CallbackDeletePopupComponent
  ],
  entryComponents: [
    Pg8583CallbackComponent,
    Pg8583CallbackUpdateComponent,
    Pg8583CallbackDeleteDialogComponent,
    Pg8583CallbackDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PgkernelgatewayPg8583CallbackModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
