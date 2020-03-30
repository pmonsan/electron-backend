/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { PgkernelgatewayTestModule } from '../../../test.module';
import { BeneficiaryTypeComponent } from 'app/entities/beneficiary-type/beneficiary-type.component';
import { BeneficiaryTypeService } from 'app/entities/beneficiary-type/beneficiary-type.service';
import { BeneficiaryType } from 'app/shared/model/beneficiary-type.model';

describe('Component Tests', () => {
  describe('BeneficiaryType Management Component', () => {
    let comp: BeneficiaryTypeComponent;
    let fixture: ComponentFixture<BeneficiaryTypeComponent>;
    let service: BeneficiaryTypeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgkernelgatewayTestModule],
        declarations: [BeneficiaryTypeComponent],
        providers: []
      })
        .overrideTemplate(BeneficiaryTypeComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(BeneficiaryTypeComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(BeneficiaryTypeService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new BeneficiaryType(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.beneficiaryTypes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
