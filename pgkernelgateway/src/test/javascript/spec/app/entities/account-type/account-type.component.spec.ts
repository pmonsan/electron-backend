/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { PgkernelgatewayTestModule } from '../../../test.module';
import { AccountTypeComponent } from 'app/entities/account-type/account-type.component';
import { AccountTypeService } from 'app/entities/account-type/account-type.service';
import { AccountType } from 'app/shared/model/account-type.model';

describe('Component Tests', () => {
  describe('AccountType Management Component', () => {
    let comp: AccountTypeComponent;
    let fixture: ComponentFixture<AccountTypeComponent>;
    let service: AccountTypeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgkernelgatewayTestModule],
        declarations: [AccountTypeComponent],
        providers: []
      })
        .overrideTemplate(AccountTypeComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AccountTypeComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AccountTypeService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new AccountType(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.accountTypes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
