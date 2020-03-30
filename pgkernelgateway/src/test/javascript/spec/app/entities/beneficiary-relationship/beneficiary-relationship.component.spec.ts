/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { PgkernelgatewayTestModule } from '../../../test.module';
import { BeneficiaryRelationshipComponent } from 'app/entities/beneficiary-relationship/beneficiary-relationship.component';
import { BeneficiaryRelationshipService } from 'app/entities/beneficiary-relationship/beneficiary-relationship.service';
import { BeneficiaryRelationship } from 'app/shared/model/beneficiary-relationship.model';

describe('Component Tests', () => {
  describe('BeneficiaryRelationship Management Component', () => {
    let comp: BeneficiaryRelationshipComponent;
    let fixture: ComponentFixture<BeneficiaryRelationshipComponent>;
    let service: BeneficiaryRelationshipService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgkernelgatewayTestModule],
        declarations: [BeneficiaryRelationshipComponent],
        providers: []
      })
        .overrideTemplate(BeneficiaryRelationshipComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(BeneficiaryRelationshipComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(BeneficiaryRelationshipService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new BeneficiaryRelationship(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.beneficiaryRelationships[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
